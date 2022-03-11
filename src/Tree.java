import org.jetbrains.annotations.NotNull;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Stack;

class Tree {
    private Node rootNode;
    private String str = "";
    private int countNodes = 0;

    public Tree() {
        rootNode = null;
    }

    public String findNodeByValue(int value) {
        Node currentNode = rootNode;
        String way = " NODE";
        while (currentNode.getValue() != value) {
            if (value < currentNode.getValue()) {
                currentNode = currentNode.getLeftChild();
                way += " LEFT";
            }
            else {
                currentNode = currentNode.getRightChild();
                way += " RIGHT";
            }
            if (currentNode == null) {
                return " Элемента не существует ";
            }
        }
        return way;
    }

    public void readTreeInFile()  {
        System.out.println("Введите файл для считывания дерева ");
        while (true) {
            try (FileReader reader = new FileReader(new Scanner(System.in).nextLine())) {
                int value;
                String buff = "";
                while ((value = reader.read()) != -1) {
                    if ((char) value == ' ') {
                        this.insertNode(Integer.parseInt(buff));
                        buff = "";
                    } else
                        buff += (char) value;
                }
                return;

            }
            catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void goToTree(Node root) {
        if (root != null) {
            str += root.getValue() + " ";
            goToTree(root.getLeftChild());
            goToTree(root.getRightChild());
        }
    }

    public void saveFromFile() {
        System.out.println("Введите файл для сохранения дерева ");
        try (FileWriter writer = new FileWriter(new Scanner(System.in).nextLine())) {
            goToTree(this.rootNode);
            writer.write(str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertNode(int value) {
        Node newNode = new Node();
        newNode.setValue(value);

        if (rootNode == null) {
            rootNode = newNode;
        }
        else {
            Node currentNode = rootNode;
            Node parentNode;
            while (true) {
                parentNode = currentNode;
                if(value == currentNode.getValue()) {
                    return;
                }
                else  if (value < currentNode.getValue()) {
                    currentNode = currentNode.getLeftChild();
                    if (currentNode == null) {
                        parentNode.setLeftChild(newNode);
                        return;
                    }
                }
                else {
                    currentNode = currentNode.getRightChild();
                    if (currentNode == null) {
                        parentNode.setRightChild(newNode);
                        return;
                    }
                }
            }
        }
    }

    public boolean deleteNode(int value)
    {
        Node currentNode = rootNode;
        Node parentNode = rootNode;
        boolean isLeftChild = true;
        while (currentNode.getValue() != value) {
            parentNode = currentNode;
            if (value < currentNode.getValue()) {
                isLeftChild = true;
                currentNode = currentNode.getLeftChild();
            }
            else {
                isLeftChild = false;
                currentNode = currentNode.getRightChild();
            }
            if (currentNode == null)
                return false;
        }

        if (currentNode.getLeftChild() == null && currentNode.getRightChild() == null) {
            if (currentNode == rootNode)
                rootNode = null;
            else if (isLeftChild)
                parentNode.setLeftChild(null);
            else
                parentNode.setRightChild(null);
        }
        else if (currentNode.getRightChild() == null) {
            if (currentNode == rootNode)
                rootNode = currentNode.getLeftChild();
            else if (isLeftChild)
                parentNode.setLeftChild(currentNode.getLeftChild());
            else
                parentNode.setRightChild(currentNode.getLeftChild());
        }
        else if (currentNode.getLeftChild() == null) {
            if (currentNode == rootNode)
                rootNode = currentNode.getRightChild();
            else if (isLeftChild)
                parentNode.setLeftChild(currentNode.getRightChild());
            else
                parentNode.setRightChild(currentNode.getRightChild());
        }
        else {
            Node heir = receiveHeir(currentNode);
            if (currentNode == rootNode)
                rootNode = heir;
            else if (isLeftChild)
                parentNode.setLeftChild(heir);
            else
                parentNode.setRightChild(heir);
        }
        return true;
    }
/*        else {
            parentNode.setLeftChild(currentNode.getRightChild());
            Node leftSon = currentNode.getLeftChild();
            while (true) {
                if (parentNode.getValue() < leftSon.getValue() && parentNode.getRightChild() != null) {
                    parentNode = parentNode.getRightChild();
                }
                else if (parentNode.getValue() > leftSon.getValue() && parentNode.getLeftChild() != null) {
                    parentNode = parentNode.getLeftChild();
                }
                if (parentNode.getRightChild() == null) {
                    parentNode.setRightChild(leftSon);
                    break;
                }
                if (parentNode.getLeftChild() == null) {
                    parentNode.setLeftChild(leftSon);
                    break;
                }
            }

        }
        return true;
    }*/

    private Node receiveHeir(@NotNull Node node) {
        Node parentNode = node;
        Node heirNode = node;
        Node currentNode = node.getRightChild();
        while (currentNode != null) {
            parentNode = heirNode;
            heirNode = currentNode;
            currentNode = currentNode.getLeftChild();
        }

        if (heirNode != node.getRightChild()) {
            parentNode.setLeftChild(heirNode.getRightChild());
            heirNode.setRightChild(node.getRightChild());
        }
        return heirNode;
    }

    public void printTree() {
        Stack globalStack = new Stack();
        globalStack.push(rootNode);
        int gaps = 32;
        boolean isRowEmpty = false;
        while (isRowEmpty == false) {
            Stack localStack = new Stack();
            isRowEmpty = true;
            for (int j = 0; j < gaps; j++)
                System.out.print(' ');
            while (globalStack.isEmpty() == false) { // покуда в общем стеке есть элементы
                Node temp = (Node) globalStack.pop(); // берем следующий, при этом удаляя его из стека
                if (temp != null) {
                    System.out.print(temp.getValue()); // выводим его значение в консоли
                    localStack.push(temp.getLeftChild()); // соохраняем в локальный стек, наследники текущего элемента
                    localStack.push(temp.getRightChild());
                    if (temp.getLeftChild() != null ||
                            temp.getRightChild() != null)
                        isRowEmpty = false;
                }
                else {
                    localStack.push(null);
                    localStack.push(null);
                }
                for (int j = 0; j < gaps * 2 ; j++)
                    System.out.print(' ');
            }
            System.out.println();
            System.out.println();
            gaps /= 2;
            while (localStack.isEmpty() == false)
                globalStack.push(localStack.pop());
        }
        System.out.println();
    }

    public void getInfo() {
        System.out.println("Информация о дереве ");
        Node node = rootNode;
        while (node.getRightChild() != null) {
            node = node.getRightChild();
        }
        System.out.println("MAX: " + node.getValue());
        node = rootNode;
        while (node.getLeftChild() != null) {
            node = node.getLeftChild();
        }
        node = rootNode;
        System.out.println("MIN: " + node.getValue());
        System.out.println("Глубина дерева: " + heighTree(rootNode));

    }

    public int heighTree(Node node) {
        if (node == null) {
            return 0;
        }
        else if ((node.getLeftChild() == null) && (node.getRightChild() == null)) {
            return 0;
        }
        else if (heighTree(node.getLeftChild()) > (heighTree(node.getRightChild())))
            return heighTree(node.getLeftChild()) + 1;
        else
            return heighTree(node.getRightChild()) + 1;
    }
}