/*
 *  Copyright 2017 Patrick Favre-Bulle
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package at.favre.tools.dice.rnd;

import at.favre.lib.crypto.HKDF;
import at.favre.tools.dice.RndTool;
import at.favre.tools.dice.util.ByteUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

/**
 * Reads static and dynamic data from the machine like MAC addresses and cpu usage to pin the
 * call to this machine. This itself is not a very strong entropy source by itself, although it will
 * make it harder for an attacker to guess all of these data.
 * <p>
 * This is used as personalization of a DRBG as described in NIST SP800-90A
 * <p>
 * See: http://csrc.nist.gov/publications/nistpubs/800-90A/SP800-90A.pdf
 */
public final class PersonalizationSource implements ExpandableEntropySource {
    private static final byte[] STATIC_RANDOM = new byte[]{0x51, 0x18, (byte) 0xD3, (byte) 0xF2, (byte) 0x8B, (byte) 0xB4, (byte) 0xEB, 0x2E, (byte) 0x82, 0x47, 0x22, 0x53, (byte) 0x8D, 0x39, (byte) 0xB7, (byte) 0xB9, 0x66, (byte) 0xB0, (byte) 0xAE, 0x31, (byte) 0xFA, (byte) 0xB7, 0x23, (byte) 0xED, 0x2E, (byte) 0xD9, (byte) 0x85, (byte) 0x94, (byte) 0xFE, (byte) 0xE6, 0x0F, 0x78, (byte) 0xAF, (byte) 0xA9, (byte) 0xC5, (byte) 0xAE, (byte) 0xE2, (byte) 0xD0, 0x19, (byte) 0xEC, 0x5B, 0x29, (byte) 0x80, 0x43, 0x7D, 0x73, (byte) 0xB7, (byte) 0xD3, (byte) 0xB7, 0x0C, (byte) 0xED, (byte) 0xF4, (byte) 0xF1, (byte) 0x8C, 0x15, (byte) 0xE0, 0x0A, (byte) 0x85, (byte) 0x9B, (byte) 0xB5, 0x15, (byte) 0xF5, (byte) 0x9C, (byte) 0xBF};
    private static final byte[] SALT = new byte[]{(byte) 0xDE, 0x56, (byte) 0xA9, (byte) 0xDB, 0x23, 0x52, (byte) 0x98, (byte) 0xF0, 0x5F, 0x26, 0x0D, 0x36, 0x19, 0x4C, 0x55, (byte) 0xA8};

    private byte[] readTempDirContent() {
        File f = new File(System.getProperty("java.io.tmpdir"));
        int count = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(f.toPath())) {
            // We use a Random object to choose what file names
            // should be used. Otherwise on a machine with too
            // many files, the same first 1024 files always get
            // used. Any, We make sure the first 512 files are
            // always used.
            Random r = new Random();
            for (Path entry : stream) {
                if (count < 512 || r.nextBoolean()) {
                    bos.write(entry.getFileName().toString().getBytes());
                }
                if (count++ > 1024) {
                    break;
                }
            }
        } catch (Exception e) {
            bos.write(e.hashCode());
        }
        return bos.toByteArray();
    }

    private byte[] concatMacAddresses() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            if (ni.getHardwareAddress() != null) {
                bos.write(ni.getHardwareAddress());
            }
        }
        return bos.toByteArray();
    }

    private byte[] runtimeData() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        final Runtime rt = Runtime.getRuntime();

        bos.write(runtimeMXBean.getName().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getVmName().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getVmVendor().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getVmVersion().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getClassPath().getBytes(StandardCharsets.UTF_8));
        bos.write(ByteBuffer.allocate(Long.BYTES).putLong(runtimeMXBean.getStartTime()).array());

        bos.write(ByteBuffer.allocate(Long.BYTES).putLong(rt.totalMemory()).array());
        bos.write(ByteBuffer.allocate(Long.BYTES).putLong(rt.freeMemory()).array());

        return bos.toByteArray();
    }

    private byte[] osData() throws IOException {
        final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(ByteBuffer.allocate(Integer.BYTES).putInt(osBean.getAvailableProcessors()).array());
        bos.write(ByteBuffer.allocate(Double.BYTES).putDouble(osBean.getSystemLoadAverage()).array());
        bos.write(osBean.getArch().getBytes(StandardCharsets.UTF_8));
        bos.write(osBean.getVersion().getBytes(StandardCharsets.UTF_8));
        return bos.toByteArray();
    }

    private byte[] appVersionData() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String version = RndTool.class.getPackage().getImplementationVersion();
        if (version != null) {
            bos.write(version.getBytes(StandardCharsets.UTF_8));
        }

        return bos.toByteArray();
    }

    private byte[] scmData() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream("git.properties"));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bos.write(String.valueOf(properties.get("git.commit.id")).getBytes(StandardCharsets.UTF_8));
        bos.write(String.valueOf(properties.get("git.commit.user.email")).getBytes(StandardCharsets.UTF_8));
        bos.write(String.valueOf(properties.get("git.commit.time")).getBytes(StandardCharsets.UTF_8));
        bos.write(String.valueOf(properties.get("git.build.time")).getBytes(StandardCharsets.UTF_8));
        bos.write(String.valueOf(properties.get("git.branch")).getBytes(StandardCharsets.UTF_8));
        bos.write(String.valueOf(properties.get("git.build.host")).getBytes(StandardCharsets.UTF_8));
        bos.write(String.valueOf(properties.get("git.dirty")).getBytes(StandardCharsets.UTF_8));
        return bos.toByteArray();
    }

    private byte[] envVariables() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            if (entry.getKey() != null) {
                bos.write(entry.getKey().getBytes(StandardCharsets.UTF_8));
            }
            if (entry.getValue() != null) {
                bos.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
            }
        }
        return bos.toByteArray();
    }

    private byte[] systemProperties() throws IOException {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Enumeration enumeration = System.getProperties().propertyNames();
            while (enumeration.hasMoreElements()) {
                String key = enumeration.nextElement().toString();
                if (key != null) {
                    String value = System.getProperty(key);
                    bos.write(key.getBytes(StandardCharsets.UTF_8));
                    if (value != null) {
                        bos.write(value.getBytes(StandardCharsets.UTF_8));
                    }
                }
            }
            return bos.toByteArray();
        } catch (SecurityException e) {
            //ignore security exception
            return new byte[0];
        }
    }


    @Override
    public byte[] generateEntropy(int lengthByte) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(STATIC_RANDOM);
            bos.write(concatMacAddresses());
            bos.write(runtimeData());
            bos.write(osData());
            bos.write(appVersionData());
            bos.write(scmData());
            bos.write(envVariables());
            bos.write(systemProperties());
            bos.write(readTempDirContent());
            bos.write(InetAddress.getLocalHost().toString().getBytes());
            return HKDF.fromHmacSha512().extractAndExpand(SALT, bos.toByteArray(), this.getClass().getName().getBytes(StandardCharsets.UTF_8), lengthByte);
        } catch (Exception e) {
            throw new IllegalStateException("could not personalization seed", e);
        }
    }

    @Override
    public String getInformation() {
        return "Personalization Source (" + ByteUtils.bytesToHex(generateEntropy(2)) + ")";
    }
}
