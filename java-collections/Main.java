public class Main {
    public static void main(String[] args) {
        Cache<String> cache = new Cache<>(2);

        System.out.println("Дабавляем элименты:");
        cache.add("A");
        cache.add("B");
        System.out.println(cache);

        System.out.println("\nДабавлием элемент 'D'");
        cache.add("D");
        System.out.println(cache);

        System.out.println("\n'B' сущ?: " + cache.exists("B"));
        System.out.println("'A' сущ?: " + cache.exists("A"));

        System.out.println("\nУдаляем 'C': " + cache.remove("C"));
        System.out.println(cache);

        System.out.println("\nПервый элемент: " + cache.getFirst());
        System.out.println("Ласт элемент: " + cache.getLast());

        System.out.println("\nЭлемент по индексу 1: " + cache.getItemByIndex(1));
        System.out.println("Элемент по индексу 8: " + cache.getItemByIndex(8));
    }
}
