package util;

import entity.*;
import org.apache.commons.io.FileUtils;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.util.FileUtil;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceUtil {
    private static long MB = 1024 * 1024;

    private HardwareAbstractionLayer hal;

    private Node node;

    public void init() {
        hal = new SystemInfo().getHardware();
        node = new Node();
    }

    public Node getNode() {
        return node;
    }

    public void getMemory() {
        try {
            Memory memory = new Memory();
            memory.setSize(hal.getMemory().getTotal());
            memory.setAvail(hal.getMemory().getAvailable());
            memory.setUsed(1-((double) hal.getMemory().getAvailable() / (double) hal.getMemory().getTotal()));

            /*内存频率*/
            if (hal.getMemory().getPhysicalMemory().size() != 0) {
                PhysicalMemory physicalMemory = hal.getMemory().getPhysicalMemory().get(0);
                memory.setFreq(physicalMemory.getClockSpeed());
                /*内存类型*/
                memory.setType(physicalMemory.getMemoryType());
            }

            /*缓存内存*/
            String meminfo = FileUtils.readFileToString(new File("/proc/meminfo"), StandardCharsets.UTF_8);

            Pattern pattern = Pattern.compile("Cached:\\s+(\\d*).*");
            Matcher matcher = pattern.matcher(meminfo);
            if (matcher.find())
                memory.setCache(Long.parseLong(matcher.group(1)));

            /*free内存*/
            pattern = Pattern.compile("MemFree:\\s+(\\d*).*");
            matcher = pattern.matcher(meminfo);
            if (matcher.find())
                memory.setFree(Long.parseLong(matcher.group(1)));
            node.setMemory(memory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getDisk() {
        File file = new File("/");
        Disk disk = new Disk();
        disk.setDiskSize(file.getTotalSpace());
        disk.setDiskUsed(1 - ((double) file.getFreeSpace() / (double) file.getTotalSpace()));
        node.setDisk(disk);
    }

    public void getNetwork() {
        try {
            List<Network> networks = new Vector<>();

            List<NetworkIF> networkIFS = hal.getNetworkIFs();
            for (NetworkIF networkIF : networkIFS) {
                /*开始计算*/
                long start_send = networkIF.getBytesSent();
                long start_recv = networkIF.getBytesRecv();
                long start_timestamp = networkIF.getTimeStamp();//ms为单位

                Thread.sleep(400);
                networkIF.updateAttributes();

                long end_send = networkIF.getBytesSent();
                long end_recv = networkIF.getBytesRecv();
                long end_timestamp = networkIF.getTimeStamp();
                double send = (end_send - start_send) * 1.0 / (end_timestamp - start_timestamp) / 1000.0;
                double recv = (end_recv - start_recv) * 1.0 / (end_timestamp - start_timestamp) / 1000.0;

                Network network = new Network();
                network.setSend(send);
                network.setRecv(recv);
                network.setName(networkIF.getName());
//                network.setType(networkIF.getIfAlias());

                if (networkIF.getIPv4addr().length != 0)
                    network.setIpv4(networkIF.getIPv4addr()[0]);

                if (networkIF.getIPv6addr().length != 0)
                    network.setIpv6(networkIF.getIPv6addr()[0]);

                networks.add(network);
            }

            node.setNetwork(networks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getCPU() {
        try {
            CentralProcessor processor = hal.getProcessor();

            /*睡眠0.1s*/
            long[] prevTicks = processor.getSystemCpuLoadTicks();
            // 睡眠200ms
            TimeUnit.MILLISECONDS.sleep(400);
            long[] ticks = processor.getSystemCpuLoadTicks();
            long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
            long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
            long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
            long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
            long cSys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
            long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
            long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
            long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
            long totalCpu = user + nice + cSys + idle + iowait + irq + softirq + steal;

            /*获取cpu信息*/
            CPU cpu = new CPU();
            cpu.setPhysicalCores(processor.getPhysicalProcessorCount());
            cpu.setCores(processor.getLogicalProcessorCount());
            cpu.setUsed(1.0 - (idle * 1.0 / totalCpu));
            cpu.setCurrentFreq(Arrays.stream(processor.getCurrentFreq()).average().orElse(Double.NaN));
            cpu.setPackageCount(processor.getPhysicalPackageCount());//插槽数

            /*厂商信息*/
            cpu.setName(processor.getProcessorIdentifier().getName());
            cpu.setFreq(processor.getProcessorIdentifier().getVendorFreq());

            /*系统运行时间*/
            RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
            cpu.setUpTime(runtimeMXBean.getUptime());
            /*线程进程数*/
            cpu.setThreadCount(ManagementFactory.getThreadMXBean().getThreadCount());
            cpu.setProcessCount(Integer.parseInt(Objects.requireNonNull(ExecUtil.execToString("sh", "process.sh").trim())));


            /*cpu温度*/
            Sensors sensors = hal.getSensors();
            double cpuTemperature = sensors.getCpuTemperature();
            cpu.setTemperature(cpuTemperature);


            node.setCpu(cpu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGPU() {
        try {
            List<GPU> gpus = new Vector<>();

            String gpuNameInfo = ExecUtil.execToString("nvidia-smi", null, "--query-gpu=name", "--format=csv");
            gpuNameInfo = gpuNameInfo.replace(" %", "");
            String[] gpuNameInfos = gpuNameInfo.split("\n");
            String[] gpuNames = Arrays.copyOfRange(gpuNameInfos, 1, gpuNameInfos.length);

            for (String gpuName : gpuNames) {
                GPU gpu = new GPU();
                gpu.setName(gpuName);
                gpus.add(gpu);
            }

            /*使用率*/
            String gpuUsedInfo = ExecUtil.execToString("nvidia-smi", null, "--query-gpu=utilization.gpu", "--format=csv");
            gpuUsedInfo = gpuUsedInfo.replace(" %", "");
            String[] gpuUsedInfos = gpuUsedInfo.split("\n");
            String[] gpuUseds = Arrays.copyOfRange(gpuUsedInfos, 1, gpuUsedInfos.length);

            /*温度*/
            String gpuTempInfo = ExecUtil.execToString("nvidia-smi", null, "--query-gpu=temperature.gpu", "--format=csv");
            String[] gpuTempInfos = gpuTempInfo.split("\n");
            String[] gpuTemps = Arrays.copyOfRange(gpuTempInfos, 1, gpuTempInfos.length);

            /*驱动器版本*/
            String gpuDriverInfo = ExecUtil.execToString("nvidia-smi", null, "--query-gpu=driver_version", "--format=csv");
            String[] gpuDriverInfos = gpuDriverInfo.split("\n");
            String[] gpuDrivers = Arrays.copyOfRange(gpuDriverInfos, 1, gpuDriverInfos.length);

            /*vbios版本*/
            String gpuVbiosInfo = ExecUtil.execToString("nvidia-smi", null, "--query-gpu=vbios_version", "--format=csv");
            String[] gpuVbiosInfos = gpuVbiosInfo.split("\n");
            String[] gpuVbios = Arrays.copyOfRange(gpuVbiosInfos, 1, gpuVbiosInfos.length);

            /*总显存*/
            String memorySizeInfo = ExecUtil.execToString("nvidia-smi", null, "--query-gpu=memory.total", "--format=csv");
            memorySizeInfo = memorySizeInfo.replace(" MiB", "");
            String[] memorySizeInfos = memorySizeInfo.split("\n");
            String[] memorySizes = Arrays.copyOfRange(memorySizeInfos, 1, memorySizeInfos.length);

            /*已用显存*/
            String memoryUsedInfo = ExecUtil.execToString("nvidia-smi", null, "--query-gpu=memory.used", "--format=csv");
            memoryUsedInfo = memoryUsedInfo.replace(" MiB", "");
            String[] memoryUsedInfos = memoryUsedInfo.split("\n");
            String[] memoryUseds = Arrays.copyOfRange(memoryUsedInfos, 1, memorySizeInfos.length);

            for (int i = 0; i < gpuUseds.length; i++) {
                if (Double.parseDouble(gpuUseds[i]) != 0)
                    gpus.get(i).setUsed(Double.parseDouble(gpuUseds[i]) / 100.0);
                else
                    gpus.get(i).setUsed(Double.parseDouble(gpuUseds[i]));

                gpus.get(i).setMemorySize(Long.parseLong(memorySizes[i]));
                gpus.get(i).setMemoryUsed(Long.parseLong(memoryUseds[i]));
                gpus.get(i).setTemperature(Double.parseDouble(gpuTemps[i]));
                gpus.get(i).setDriverVersion(gpuDrivers[i]);
                gpus.get(i).setVbiosVersion(gpuVbios[i]);

            }

            node.setGpus(gpus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
