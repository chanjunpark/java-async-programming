import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static java.util.stream.Collectors.toList;

public class PriceServiceImplNonBlock implements PriceService{

    public static void main(String[] args) {
        PriceService priceService = new PriceServiceImplNonBlock();
        priceService.showPrice();
    }

    @Override
    public void showPrice() {
        long start = System.nanoTime();
//        System.out.println(findPricesSync("Iphone13pro"));
//        System.out.println(findPricesSyncParallel("Iphone13pro"));
        System.out.println(findPricesAsync("Iphone13pro"));
        long duration = ((System.nanoTime()-start) / 1_000_000);
        System.out.println("Done in " + duration + " msecs");
    }

    public List<String> findPricesSync(String product) {
        List<Shop> shops = createShopList();
        return shops.stream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    public List<String> findPricesSyncParallel(String product) {
        List<Shop> shops = createShopList();
        return shops.parallelStream()
                .map(shop -> String.format("%s price is %.2f", shop.getName(), shop.getPrice(product)))
                .collect(toList());
    }

    public List<String> findPricesAsync(String product) {
        List<Shop> shops = createShopList();
        List<CompletableFuture<String>> priceFutures =
                shops.stream()
                        .map(shop -> CompletableFuture.supplyAsync(
                                () -> shop.getName() + " price is " + shop.getPrice(product)))
                        .collect(toList());
        return priceFutures.stream()
                .map(CompletableFuture::join)
                .collect(toList());
    }

    public List<Shop> createShopList() {
        return Arrays.asList(new Shop("Amazon"),
                new Shop("Naver"),
                new Shop("Coupang"),
                new Shop("11st"));
    }

}
