package at.favre.tools.dice.rnd;

import at.favre.tools.dice.RndTool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.Properties;

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
    private static final byte[] SALT = new byte[]{(byte) 0xDE, 0x56, (byte) 0xA9, (byte) 0xDB, 0x23, 0x52, (byte) 0x98, (byte) 0xF0, 0x5F, 0x26, 0x0D, 0x36, 0x19, 0x4C, 0x55, (byte) 0xA8};

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

        bos.write(runtimeMXBean.getName().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getVmName().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getVmVendor().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getVmVersion().getBytes(StandardCharsets.UTF_8));
        bos.write(runtimeMXBean.getClassPath().getBytes(StandardCharsets.UTF_8));
        bos.write(ByteBuffer.allocate(Long.BYTES).putLong(runtimeMXBean.getStartTime()).array());
        bos.write(ByteBuffer.allocate(Long.BYTES).putLong(runtimeMXBean.getUptime()).array());

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

    @Override
    public byte[] generateEntropy(int lengthByte) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(concatMacAddresses());
            bos.write(runtimeData());
            bos.write(osData());
            bos.write(appVersionData());
            bos.write(scmData());
            return HKDF.hkdf(bos.toByteArray(), SALT, SALT, lengthByte);
        } catch (Exception e) {
            throw new IllegalStateException("could not personalization seed", e);
        }
    }
}
