package heap;

/**
 * @author wangchong
 * @date 2019/6/6 19:39
 * @email 876459397@qq.com
 * @CSDN https://blog.csdn.net/wfcn_zyq
 * @describe
 */
public class HeapTest {
    public static void main(String[] args) {
        Heap<Integer> arr = new Heap <>();
        int[] nums = new int[]{1,3,4,3,4,23,121,323,32,24};
        for (int i : nums
             ) {
            arr.add(i);
        }
        arr.add(212);
        System.out.println(arr.toString());
    }
}
