import java.math.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public class Main {

    public static void main(String[] args) {
        Shop shop = new Shop();
        long start = System.nanoTime();
        Future<Double> futurePrice = shop.getPriceAsync("my favorite product"); // 상점에 제품가격 정보 요청
        long invocationTime = ((System.nanoTime()-start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        try {
            double price = futurePrice.get(); // 가격 정보가 있으면 Future에서 가격 정보를 읽고, 없으면 가격 정보를 받을 때까지 블록한다.
            System.out.printf("Price is %.2f%n", price);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + " msecs");
//        System.out.println(new Main().getPrice("15"));
    }
}
