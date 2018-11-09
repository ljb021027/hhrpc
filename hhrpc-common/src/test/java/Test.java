import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author liujiabei
 * @since 2018/10/30
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
//        AtomicInteger num = new AtomicInteger();
//        for (int i = 0; i < 1000; i++) {
//            new Thread(() -> {
//                num.getAndIncrement();
//            }).start();
//        }
//        Thread.sleep(100L);
//        System.out.println(num.get());

        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(2);
        list.add(3);
        list.add(4);
        System.out.println("----------list大小1：--"+list.size());
        Iterator<Integer> it = list.iterator();
        while(it.hasNext()){
            Integer item = it.next();
            if (2 == item) {
                it.remove();
            }
        }

        }
}
