import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;

public class Voter {
    int id; // 投票者的编号
    int number; // 所有投票者的数目
    private int digit; // 补充后的二进制具有几位数
    private String ips[]; // 所有投票者的地址
    Candidate candidate; // 候选人信息
    String binary; // 二进制选票信息
    BigInteger decimal; // 十进制选票信息
    BigInteger randomDec[]; // 十进制选票信息随机分成number份
    String receiveMsg[]; // 从其他投票者收到的信息
    int result[]; // 每个候选人的得票数

    /**
     * 构造函数
     * 
     * @param number    投票者的数目
     * @param candidate 候选人信息
     */
    public Voter(int number, Candidate candidate, boolean voteMsg[], String ipMsg[]) {
        this.number = number;
        this.candidate = candidate;
        digit = (int) (Math.log(number) / Math.log(2)) + 1;// 计算位数
        this.setBinary(voteMsg);
        this.setDecimal();
        this.setIps(ipMsg);
        this.setRadomDec();
        // ==================================================
        // ==== 数据收发
        // ==================================================
        // this.getResult();
    }

    /**
     * 设置二进制选票信息
     * 
     * @param voteMsg 投票信息，表示该投票人对每一个候选人是否投票
     */
    public void setBinary(boolean voteMsg[]) {
        int digit;
        for (int i = 0; i < candidate.number; i++) {
            // 每一组先补digit-1个0
            digit = this.digit;
            while (--digit != 0) {
                if (binary == null) {
                    binary = "0";
                } else {
                    binary = binary.concat("0");
                }
            }

            // 投了在右边补1，没投补0
            if (voteMsg[i] == true) {
                binary = binary.concat("1");
            } else {
                binary = binary.concat("0");
            }
        }
    }

    /**
     * 把decimal随机分成number份，放在radomDec中
     * 
     */
    private void setRadomDec() {
        randomDec = new BigInteger[number];
        BigDecimal bigDecimal[] = new BigDecimal[number - 1]; // 需要number-1个随机数
        for (int i = 0; i < number - 1; i++) { // 生成number-1个随机数
            bigDecimal[i] = new BigDecimal(decimal);
            bigDecimal[i] = bigDecimal[i].multiply(new BigDecimal(Math.random())).setScale(0, RoundingMode.HALF_UP); // 对生成的随机数进行四舍五入
        }
        Arrays.sort(bigDecimal); // 随机数组升序排序

        // 根据随机数分段求每段的大小
        randomDec[0] = bigDecimal[0].toBigInteger();
        for (int i = 1; i < number - 1; i++) {
            randomDec[i] = bigDecimal[i].toBigInteger().subtract(bigDecimal[i - 1].toBigInteger());
        }
        randomDec[number - 1] = decimal.subtract(bigDecimal[number - 2].toBigInteger());
    }

    /**
     * 把二进制字符串s分成k组，每组digit个数字，输出转化成十进制的int数组
     * 
     * @param s     二进制字符串
     * @param digit 每组的数字数
     * @return 返回int型的含有k个数的数组
     */
    public int[] divideGroup(String s, int digit) {
        int k; // k是分成的组数
        int MOD = s.length() % digit;// MOD是分组的余数
        if (MOD == 0) {
            k = s.length() / digit;
        } else {
            k = s.length() / digit + 1;
        }
        int[] group = new int[k];

        String subString;
        if (MOD != 0) {
            subString = s.substring(0, MOD);
            group[0] = Integer.parseInt(subString, 2);// 有余数，把0到MOD位的二进制数转到group[0]
        } else {
            subString = s.substring(0, digit);
            group[0] = Integer.parseInt(subString, 2);// 没余数，直接把前n位转到group[0]
        }
        for (int i = 1; i < k; i++) {
            subString = s.substring(i * digit, (i + 1) * digit);
            group[i] = Integer.parseInt(subString, 2);// 接下来每n位转到group[i]中
        }
        return group;
    }

    /**
     * 用二进制计算十进制选票信息
     */
    public void setDecimal() {
        decimal = new BigInteger(binary, 2);// 这个函数把2进制的字符串转化为大整数类（十进制）
    }

    /**
     * 对receiveMsg求和计算result
     */
    public void getResult() {
        BigInteger resultBigInteger = new BigInteger("0");
        for (int i = 0; i < candidate.number; i++) {
            resultBigInteger = resultBigInteger.add(new BigInteger(receiveMsg[i]));// 对receiveMsg求和
        }
        String resultString = resultBigInteger.toString(2);// 转化为二进制字符串

        result = divideGroup(resultString, digit); // 二进制字符串分组计算出result
    }

    /**
     * 储存所有投票者的地址
     * 
     * @param ips
     */
    public void setIps(String ips[]) {
        this.ips = ips;
    }

    /**
     * 发送信息
     * 
     * @param msg
     * @param ip
     */
    public void send(String msg, String ip) {
        // TODO
    }

    /**
     * 接收信息
     * 
     * @param msg
     * @param ip
     */
    public void receive(String msg, String ip) {
        // TODO
    }
}
