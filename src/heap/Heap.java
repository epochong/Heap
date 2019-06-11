package heap;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author wangchong
 * @date 2019/6/6 19:10
 * @email 876459397@qq.com
 * @CSDN https://blog.csdn.net/wfcn_zyq
 * @describe 基于数组实现的二叉堆
 */
public class Heap<E> {
    private Comparator<E> comparator;
    private int size;
    private E[] elementData;
    private static final int DEFAULT_CAPACITY = 10;
    public Heap() {
        this(DEFAULT_CAPACITY,null);
    }

    /**
     * 构建一个指定容量的二叉堆
     * @param initialCapacity 指定容量
     */
    public Heap(int initialCapacity) {
        this(initialCapacity,null);
    }

    /**
     * 构建一个空的指定容量，指定比较器的二叉堆
     * @param initialCapacity 指定容量
     * @param comparator 指定的比较器
     */
    public Heap(int initialCapacity, Comparator<E> comparator) {
        elementData = (E[]) new Object[initialCapacity];
        this.comparator = comparator;
    }

    /**
     * heapify 将任意数组转换为二叉堆
     * @param arr 任意数组
     */
    public Heap(E[] arr) {
        elementData = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++) {
            elementData[i]= arr[i];
        }
        size = elementData.length;
        for (int i = (arr.length - 1 - 1) / 2; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 获取堆的大小
     * @return 堆的大小
     */
    public int getSize() {
        return size;
    }

    /**
     * 判断堆是否为空
     * @return true为空，false为非空
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 在堆的最后一个位置添加元素，添加的元素要上浮
     * @param e 添加的元素
     */
    public void add(E e) {
        if (size == elementData.length) {
            grow();
        }
        elementData[size++] = e;
        siftUp(size - 1);
    }

    /**
     * 添加元素后如果超出堆的容量，扩展容量
     * 在容量小于64的时，每次扩展为原来的两倍，超过64扩展为原来的1.5倍
     * 当扩展后超过Integer.MAX_VALUE 不会再扩展，抛出数组太大的异常
     */
    private void grow() {
        int oldCap = elementData.length;
        int newCap = oldCap + (oldCap < 64 ? oldCap : oldCap >> 1);
        if (newCap > Integer.MAX_VALUE - 8) {
            throw new IndexOutOfBoundsException("数组太大");
        }
        //将原数组扩展为原来的新长度的方法，Arrays.copyOf(arr,newCapacity)返回arr的新长度的副本，多余出的位置为null
        elementData = Arrays.copyOf(elementData,newCap);
    }

    /**
     * 堆元素上浮
     * @param index 上浮的位置
     */
    private void siftUp(int index) {
        while (index > 0 && compare(elementData[index],elementData[parentIndex(index)]) > 0) {
            swap(index,parentIndex(index));
            index = parentIndex(index);
        }

    }
    private void swap(int i, int j) {
        E tem = elementData[i];
        elementData[i] = elementData[j];
        elementData[j] = tem;
    }
    private int compare(E o1,E o2) {
        if (comparator == null) {
            return ((Comparable<E>)o1).compareTo(o2);
        }
        return comparator.compare(o1,o2);
    }
    private int leftChildIndex(int index) {
        return index * 2 + 1;
    }
    private int rightChildIndex(int index) {
        return index * 2 + 2;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (E e : elementData
             ) {
            if (e != null) {
                res.append(e + " ");
            }
        }
        return res.toString();

    }

    public E findMax() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("没有元素");
        }
        return elementData[0];
    }
    public E extractMax() {
        E result = findMax();
        swap(0,size - 1);
        elementData[--size] = null;
        siftDown(0);
        return result;
    }

    public E replace(E newValue) {
        E result = findMax();
        elementData[0] = newValue;
        //下沉
        siftDown(0);
        return result;
    }
    private void siftDown(int index) {
        while (leftChildIndex(index) < size) {
            //当前节点左孩子节点下标
            int j = leftChildIndex(index);
            //判断是做孩纸大还是右孩子大
            if (j + 1 < size) {
                if (compare(elementData[j],elementData[j + 1]) < 0) {
                    j++;
                }
                if (compare(elementData[index],elementData[j]) > 0) {
                    break;
                }
                swap(index,j);
                index = j;
            }

        }
    }
    private int parentIndex(int index) {
        if (index == 0) {
            throw  new IllegalArgumentException("根结点没有父节点");
        }
        return (index - 1) / 2;
    }
}
