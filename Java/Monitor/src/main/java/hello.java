import util.DeviceUtil;

public class hello {
    public static void main(String[] args) {

        // 开始时间
        long stime = System.currentTimeMillis();


        DeviceUtil deviceUtil = new DeviceUtil();
        deviceUtil.init();

//        deviceUtil.getMemory();
//        deviceUtil.getCPU();
        deviceUtil.getGPU();
//        deviceUtil.getNetwork();
        System.out.println(deviceUtil.getNode());

        // 结束时间
        long etime = System.currentTimeMillis();
        // 计算执行时间
        System.out.printf("exec time：%x ms.", (etime - stime));
    }
}