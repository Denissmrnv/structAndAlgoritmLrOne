import java.util.Scanner;

public class MAIN {
    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.readTreeInFile();
        while (true) {
            System.out.println("\nВыберите действие над деревом: \n" +
                    " 1 Вывод дерева \n" +
                    " 2 Построить путь до введенного элемента \n" +
                    " 3 Вставить узел \n" +
                    " 4 Удалить узел \n" +
                    " 5 Сохранить дерево в заданный файл \n" +
                    " 6 Информация о дереве \n");
            switch (new Scanner(System.in).next().charAt(0)) {
                case '1':
                    tree.printTree();
                    break;
                case '2':
                    System.out.println("Введите элемент");
                    System.out.println(tree.findNodeByValue(new Scanner(System.in).nextInt()));
                    break;
                case '3':
                    System.out.println("Введите элемент ");
                    tree.insertNode(new Scanner(System.in).nextInt());
                    break;
                case '4':
                    System.out.println("Введите элемент ");
                    tree.deleteNode(new Scanner(System.in).nextInt());
                    break;
                case '5':
                    tree.saveFromFile();
                    break;
                case '6':
                    tree.getInfo();
                    break;
                default:
                    System.out.println("Некорректная операция");
            }
        }
    }
}
