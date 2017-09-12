package at.favre.tools.dice.rnd;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public final class FingerprintEntropySource implements EntropySource {
    private static final byte[] SALT = new byte[]{(byte) 0xDE, 0x56, (byte) 0xA9, (byte) 0xDB, 0x23, 0x52, (byte) 0x98, (byte) 0xF0, 0x5F, 0x26, 0x0D, 0x36, 0x19, 0x4C, 0x55, (byte) 0xA8};

    private final static int INTERNAL_SEED_LENGTH = 32;

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

    @Override
    public byte[] generateEntropy() {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bos.write(concatMacAddresses());
            bos.write(runtimeData());
            bos.write(osData());
            return HKDF.hkdf(bos.toByteArray(), SALT, SALT, INTERNAL_SEED_LENGTH);
        } catch (Exception e) {
            throw new IllegalStateException("could not generate seed", e);
        }
    }
}
