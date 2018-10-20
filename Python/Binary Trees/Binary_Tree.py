#!/usr/bin/env python3

class Node:

    left = None
    right = None
    parent = None
    data = None

    def __init__(self, data = None, parent = None):
        self.data = data
        self.parent = parent

    def set_left(self, left_node):
        self.left = left_node

    def set_right(self, right_node):
        self.right = right_node

    def is_external(self):
        return (self.left is None) and (self.right is None)

    def is_internal(self):
        return (self.left is not None) and (self.right is not None)


class BinaryTree:

    root = None

    def __init__(self, root = None):
        if isinstance(root, Node):
            self.root = root
        else:
            print("Tree can have root of Node type only")

    def left_subtree(self):
        return BinaryTree(self.root.left)

    def right_subtree(self):
        return BinaryTree(self.root.right)

    def contains(self, item):
        if self.root is None:
            return False
        if self.root.data == item:
            return True
        return self.left_subtree().contains(item) or self.right_subtree().contains(item)

    def get_node(self, node_data):
        if not self.contains(node_data):
            print("There is no node with data "+str(node_data))
            return
        else:
            if self.root.data == node_data:
                return self.root
            elif self.root.left is not None and self.left_subtree().contains(node_data):
                return self.left_subtree().get_node(node_data)
            else:
                return self.right_subtree().get_node(node_data)

    def add_child(self, parent, child):
        if not self.contains(parent):
            print("There is no node with data "+str(parent))
            return
        if self.get_node(parent).is_internal():
            print("The node with data "+str(parent)+" already has both child")
            return
        else:
            parent_node = self.get_node(parent)
            if parent_node.left is None:
                parent_node.set_left(Node(child, parent_node))
            else:
                parent_node.set_right(Node(child, parent_node))


    def print_tree(self, space=0):
        if self.root is None:
            return
        self.left_subtree().print_tree(space+2)
        for _ in range(space):
            print("  ", end="")
        print(str(self.root.data))
        self.right_subtree().print_tree(space+2)
