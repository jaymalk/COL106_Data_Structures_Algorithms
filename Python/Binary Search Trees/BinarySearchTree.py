#!/usr/bin/env python3

"""Binary Search Tree"""

class BinarySearchTree:
    """Class for the tree structure, having a inbuilt node class and functions."""
    class Node:
        """Class for the nodes of the BinarySearchTree,
            Has 5 non_public parameters
            3 node-link related,
            1 holding the data,
            1 for the key.
        """
        _left = None
        _right = None
        _parent = None
        _data = None
        _key = None
        def __init__(self, key, data=None):
            """Create new node given a key and data"""
            self._data = data
            self._key = key
        def __str__(self):
            return "("+str(self._key)+","+str(self._data)+")"
        # BASIC NODE OPERATIONS
        def set_parent(self, parent):
            """Set a parent for the node. If the input is not a node, throw exception."""
            if isinstance(parent, type(self)) or parent is None:
                self._parent = parent
            else:
                print("Illegal input for setting parent.")
        def get_parent(self):
            """Return the parent of the node."""
            return self._parent
        def set_left(self, left):
            """Set a leftchild for the node. If the input is not a node, throw exception."""
            if isinstance(left, type(self)) or left is None:
                self._left = left
            else:
                print("Illegal input for setting left child.")
        def get_left(self):
            """Return the leftchild of the node."""
            return self._left
        def has_left(self):
            """Tell whether there is a left child."""
            return self._left is not None
        def set_right(self, right):
            """Set a rightchild for the node. If the input is not a node, throw exception."""
            if isinstance(right, type(self)) or right is None:
                self._right = right
            else:
                print("Illegal input for setting right child.")
        def get_right(self):
            """Return the right child for the node."""
            return self._right
        def has_right(self):
            """Tell whether there is a left child."""
            return self._right is not None
        def change_data(self, data):
            """Edit the data of the node."""
            self._data = data
        def get_data(self):
            """Return the data in the node."""
            return self._data
        def _change_key(self, key):
            """Edit/Change the key of a node. Strictly, used only for node deletion."""
            if isinstance(key, type(self._key)):
                self._key = key
            else:
                print("Illegal argument for changeing the key.")
        def get_key(self):
            """Return the key of the node."""
            return self._key
        # SOME OTHER IMPORTANT NODE OPERATIONS
        def is_external(self):
            """Tell whether the node is a leaf."""
            return (self._left is None) and (self._right is None)
        def is_internal(self):
            """Tell whether the node has both children."""
            return (self._left is not None) and (self._right is not None)
        def is_top(self):
            """Tell whether the node is the topmost. (root)"""
            return self._parent is None
        def is_left_child(self):
            """If node is not topmost, tell whether node is the left child."""
            if self.is_top():
                return False
            return self._parent.get_left() == self
        def is_right_child(self):
            """If node is not topmost, tell whether node is the right child."""
            if self.is_top():
                return False
            return not self.is_left_child()
        def node_height(self):
            """Return the node specefic height."""
            if self is None:
                return -1
            return 1+max(self.get_left().node_height(), self.get_right().node_height())
    def __init__(self, root=None):
        """Initialise the tree. Set a root (null or provided)."""
        if isinstance(root, BinarySearchTree.Node) or root is None:
            self._root = root
        else:
            raise Exception("Tree can't be built. Input item is not a tree node.")
    # TREE RELATED OPERATIONS
    def is_empty(self):
        """Tell whether the tree is empty or not."""
        return self._root is None
    def left_subtree(self):
        """Return the left subtree of the current tree.
            Throw exception if current tree is empty.
        """
        if not self.is_empty():
            return BinarySearchTree(self._root.get_left())
        raise Exception('Empty Tree')
    def right_subtree(self):
        """Return the right subtree of the current tree.
            Throw exception if current tree is empty.
        """
        if not self.is_empty():
            return BinarySearchTree(self._root.get_right())
        raise Exception('Empty Tree')
    def is_super_tree(self):
        """Tell whether tree is not a subtree of any other."""
        if self.is_empty():
            return True
        return self._root.get_parent() is None
    def get_super_tree(self):
        """Return the supermost tree of the current tree."""
        supertree = self
        if not self.is_super_tree():
            temp = self._root
            while temp.get_parent() is not None:
                temp = temp.get_parent()
            supertree = BinarySearchTree(temp)
        return supertree
    def get_max(self):
        """Return the item with largest key."""
        temp = self._root
        while temp.has_right():
            temp = temp.get_right()
        return temp
    def get_mint(self):
        """Return the item with smallest key."""
        temp = self._root
        while temp.has_left():
            temp = temp.get_left()
        return temp
    def add_item(self, key, data=None):
        """Add item to the tree, keeping in mind the BST property."""
        if self.is_empty():
            self._root = BinarySearchTree.Node(key, data)
        elif self._root.get_key() >= key:
            if not self._root.has_left():
                new = BinarySearchTree.Node(key, data)
                self._root.set_left(new)
                new.set_parent(self._root)
            else:
                self.left_subtree().add_item(key, data)
        else:
            if not self._root.has_right():
                new = BinarySearchTree.Node(key, data)
                self._root.set_right(new)
                new.set_parent(self._root)
            else:
                self.right_subtree().add_item(key, data)
    def delete_item(self, key, data=None):
        """Delete an item from the tree. Can be specific too."""
        node = self.get_node(key, data)
        self._delete(node)
    def _delete(self, node):
        """Delete a node from the tree."""
        if node.is_external():
            if node is self._root:
                _root = None
            else:
                if node.is_left_child():
                    node.get_parent().set_left(None)
                else:
                    node.get_parent().set_right(None)
        elif node.is_internal():
            try:
                if self.left_subtree().height() > self.right_subtree().height():
                    temp = self._predecessor(node)
                else:
                    temp = self._successor(node)
                node.change_data(temp.get_data())
                node._change_key(temp.get_key())
                self._delete(temp)
            except Exception as _e:
                print(_e)
        else:
            if node.has_left():
                node.get_left().set_parent(node.get_parent())
                if node is self._root:
                    self._root = node.get_left()
                else:
                    if node.is_left_child():
                        node.get_parent().set_left(node.get_left())
                    else:
                        node.get_parent().set_right(node.get_left())
            else:
                node.get_right().set_parent(node.get_parent())
                if node is self._root:
                    self._root = node.get_right()
                else:
                    if node.is_left_child():
                        node.get_parent().set_left(node.get_right())
                    else:
                        node.get_parent().set_right(node.get_right())
    def height(self):
        """Return the height of the tree.
            Height: maximum length of the path from root to a leaf.
        """
        if self.is_empty():
            return -1
        return 1+max(self.left_subtree().height(), self.right_subtree().height())
    # NODE RELATED OPERATIONS
    def set_root(self, root):
        """Set a new root for the tree."""
        if isinstance(root, BinarySearchTree.Node):
            self._root = root
        else:
            print("Illegal Input")
    def get_root(self):
        """Return the root of the tree."""
        return self._root
    def contains(self, key):
        """Tell whether there exists a node (with a particular key) in the tree."""
        if self.is_empty():
            return False
        if self._root.get_key() == key:
            return True
        return self.right_subtree().contains(key) or self.left_subtree().contains(key)
    def get_node(self, key, data=None):
        """Return a specefic node, incase keys are same."""
        temp = self._node(key)
        if data is not None:
            while temp.get_data() is not data:
                temp = BinarySearchTree(temp.get_left())._node(key)
        return temp
    def _node(self, key):
        """Return the node with the given key. Raise excpetion if no such node."""
        if not self.contains(key):
            raise Exception("No such node in the tree.")
        if self._root.get_key() == key:
            return self._root
        if self._root.get_key() > key:
            return self.left_subtree()._node(key)
        return self.right_subtree()._node(key)
    def get_successor(self, key):
        """Return the successor of the given key inorder."""
        if not self.contains(key):
            raise Exception('No such node.')
        node = self._node(key)
        return self._successor(node)
    def _successor(self, node):
        if node.has_right():
            temp = node.get_right()
            while temp.has_left():
                temp = temp.get_left()
            return temp
        ancestor = node.get_parent()
        temp = node
        while ancestor is not None and temp.is_right_child():
            temp = ancestor
            ancestor = ancestor.get_parent()
        if ancestor is None:
            raise Exception('This node is the largest in the tree.')
        return ancestor
    def get_predecessor(self, key):
        """Return the predecessor of the given key inorder."""
        if not self.contains(key):
            raise Exception('No such node.')
        node = self.get_node(key)
        return self._predecessor(node)
    def _predecessor(self, node):
        if node.has_left():
            temp = node.get_left()
            while temp.has_right():
                temp = temp.get_right()
            return temp
        ancestor = node.get_parent()
        temp = node
        while ancestor is not None and temp.is_left_child():
            temp = ancestor
            ancestor = ancestor.get_parent()
        if ancestor is None:
            raise Exception('This node is the smallest in the tree.')
        return ancestor
    # PRINTING THE TREE
    def inorder_print(self, space=0):
        """Print the tree in Inorder Way.
            Actually gives tree-like picture horizontally.
        """
        if self.is_empty():
            return
        self.left_subtree().inorder_print(space+4)
        for _ in range(space):
            print(' ', end=' ')
        print(str(self._root))
        self.right_subtree().inorder_print(space+4)
    def breadth_first_search_print(self):
        """Traverse the tree breadth wise and print it"""
        if self.is_empty():
            return
        node_list = []
        node_list.append(self._root)
        for item in node_list:
            if item.has_left():
                node_list.append(item.get_left())
            if item.has_right():
                node_list.append(item.get_right())
        for item in node_list:
            print(item)
