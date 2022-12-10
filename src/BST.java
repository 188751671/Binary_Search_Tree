import java.util.Scanner;

public class BST {
    static node root = null;
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while(true) {
            switch (sc.nextInt()) {
                case 0:
                    System.exit(0);
                    break;
                case 1:
                    insert(sc.nextInt());
                    break;
                case 2:
                    search(sc.nextInt());
                    break;
                case 3:
                    findMax(root);
                    break;
                case 4:
                    findMin(root);
                    break;
                case 5:
                    PreOrderPrint(root);
                    break;
                case 6:
                    PostOrderPrint(root);
                    break;
                case 7:
                    InOrderPrint(root);
                    break;
                case 8:
                    delete(root,sc.nextInt());
                    // delete2(root, sc.nextInt());
                    break;
            }
        }
    }

    public static void insert(int data){
        BinarySearch(root,data,true);
    }

    public static void search(int data){
        node node = BinarySearch(root,data,false);
        if ( node == null){
            System.out.println(data + "(0)");
        }else {
            printData(node);
        }
    }

    public static node BinarySearch(node node, int data, Boolean insert){
        if (node == null){
            if (insert){
                root = new node(data, 1, null, null);
                return root;
            }
            return null;
        }
        if (data == node.data){
            if (insert)     node.frequency++;
            return node;
        }
        if (data < node.data){
            if (insert && node.left == null){
                node newNode = new node(data,1,null,null);
                node.left = newNode;
                return newNode;
            }
            return BinarySearch(node.left,data,insert);
        }

        if (insert && node.right == null){
            node newNode = new node(data,1,null,null);
            node.right = newNode;
            return newNode;
        }
        return BinarySearch(node.right,data,insert);
    }

    static node father = null;
    static boolean leftTrue;

    public static void delete(node node, int data){
        if(node==null)  return;

        if (data == node.data){
            if (node.frequency > 1)         node.frequency--;
            else {
                node temp = null;

                if (node.left == null && node.right == null){

                    if (father != null){

                        // It doesn't have child.  The father's left/right towards the node has to be null.
                        if (leftTrue){
                            father.left = null;
                        }else{
                            father.right = null;
                        }

                    }else {     // if father is null, the root node is being deleted.

                        root = null;

                    }



                }else if (node.left != null && node.right == null){
                    // It only has left child

                    NodeSwap(node,node.left,true);

                }else if (node.left == null && node.right != null){
                    //It only has right child

                    NodeSwap(node,node.right,true);

                }else {
                    // It has both left and right children, choose the maximum from the left subtree by default
                    temp = node.left;
                    if (temp.right == null){
                        // the left child doesn't have right subtree

                        // not to change node.right  ( node is to be deleted )
                        NodeSwap(node,temp);
                        node.left = temp.left;

                    }else {
                        // the left child has right subtree
                        while (temp.right != null) {
                            father = temp;
                            temp = temp.right;
                        }
                        if (temp.left == null){
                            // if the maximum doesn't have left subtree

                            NodeSwap(node,temp);
                            father.right = null;

                        }else {
                            // if the maximum has left subtree

                            NodeSwap(node,temp);
                            father.right = temp.left;
                        }

                    }
                }

            }
            return;
        }

        if (data < node.data){
           father = node;
           leftTrue = true;
            delete(node.left,data);
        }else {
            father = node;
            leftTrue = false;
            delete(node.right,data);
        }

    }

    /** 1. 搜索功能(包括delete里)  为什么一定要用 递归1层, 而不是 直接用while找到 被删除节点?
     * while(node.data!=data)
     *      if(node.data>data){
     *          // 这里需要保存node (待会儿找到结果后的node 的父节点)
     *          node = node.right;
     *          }
     * 这里的node.data == data
     * 因为 搜索到 每个节点时,都需要保存 父节点. 这样不如利用 递归的回调,每个父节点的 递归函数的返回值 可以对本节点 进行 修改
     **/

    public static node delete2(node node, int data){
        if(node == null)    return null;

        if(data<node.data){
            node.left =  delete2(node.left,data);  // 函数只能返回node(node.right传进去的值) 或者 左边最大值节点的left节点
        }else if (data>node.data){
            node.right = delete2(node.right,data);
        }else {
            // found the node to be deleted

            if (node.left == null){         // 被删除节点 只有 一个子节点, 就返回 子节点 ( 父节点回调 在等着 重置 father.left/right)
                return node.right;
            }
            if (node.right == null){
                return node.left;           // 同上
            }

            node.data = findMax(node.left);             // 这里 被删除节点  有 左右 子节点, 我们找到 左子节点 下的最大值, 赋值给 被删除节点
                                                        // 待会儿 对最大值节点 的父亲回调 重定向给 左边节点时, 就不用再 操作 最大值节点了
            node.left = delete2(node.left,node.data);   //(算法唯一冗余的地方)  再搜索一遍 左子节点下 最大值
        }                                               // 并利用 上面 双if 返回 最大值节点的 左节点
        return node;
    }

    /**
     *  算法核心点是  双if (被删除节点的 右子空 返回左子,左子空 返回右子)  可以复用在   最大值节点上,最大值右子 肯定为空,返回左子给父
     *  所以 有双子的被删除节点 需要继续 递归,直到 最大值节点, 才全部返回
     *  最后的 return node; 返回自身节点 , 就是在 双if(返回子节点 舍弃自身)  相反的   (父 还是指向自身 不做改变)
     */


    public static int findMax(node root){
        if (root == null){
            System.out.println("0(0)");
            return 0;
        }
        node node = root;
        while(node.right!=null){
            node = node.right;
        }
        printData(node);
        return node.data;

    }

    public static int findMin(node root){
        if (root == null){
            System.out.println("0(0)");
            return 0;
        }
        node node = root;
        while(node.left!=null){
            node = node.left;
        }
        printData(node);
        return node.data;
    }


    private static void InOrderPrint(node node){
        if (node==null) return;

        InOrderPrint(node.left);

        printData(node);

        InOrderPrint(node.right);
    }

    private static void PreOrderPrint(node node){
        if (node==null) return;

        printData(node);

        PreOrderPrint(node.left);

        PreOrderPrint(node.right);
    }

    private static void PostOrderPrint(node node){
        if (node==null) return;

        PostOrderPrint(node.left);

        PostOrderPrint(node.right);

        printData(node);
    }


//  Tool functions

    private static void printData(node node){
        System.out.println(node.data + "(" + node.frequency + ")");
    }

    private static void NodeSwap(node destination, node source){
        destination.data = source.data;
        destination.frequency = source.frequency;
    }
    private static void NodeSwap(node destination, node source, boolean fullswap){
        destination.data = source.data;
        destination.frequency = source.frequency;
        destination.left = source.left;
        destination.right = source.right;
    }

}



class node{
    int data;
    int frequency;
    node left,right;

    node(int data, int frequency, node left, node right){
        this.data = data;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }
}



