#!/usr/bin/env python3

class data_type:

    def __init__(self, key, data):
        self._key = key
        self._data = data

    def get_key(self):
        return self._key

    def get_data(self):
        return self._data
        
    def __str__(self):
        return "( " + str(self._key) + ", " + str(self._data) + " )"


class max_heaps:

    def __init__(self):
        self.heap = []

    def is_empty(self):
        return len(self.heap) == 0

    def is_internal(self, ind):
        return (2*ind+2<len(self.heap))

    def insert(self, tple):
        if type(tple) != data_type:
            return "Error : Item must be a tuple"
        self.heap.append(tple)
        self.up_heapify(len(self.heap)-1)

    def up_heapify(self, ind):
        if ind == 0:
            return
        pr_ind = (ind-1)//2
        item = self.heap[ind]
        parent = self.heap[pr_ind]
        if parent.get_key() > item.get_key():
            return
        else:
            self.heap[ind] = parent
            self.heap[pr_ind] = item
            self.up_heapify(pr_ind)

    def remove_max(self):
        if self.is_empty():
            return "Error : Heap Empty"
        temp = self.heap[0]
        if len(self.heap) == 1:
            self.heap = []
            return temp
        self.heap[0] = self.heap.pop()
        self.down_heapify(0)
        return temp

    def down_heapify(self, ind):
        lft_ind = 2*ind+1
        rgt_ind = 2*ind+2
        item = self.heap[ind]
        if self.is_internal(ind):
            left = self.heap[lft_ind]
            right = self.heap[rgt_ind]
            if (right.get_key() > left.get_key()):
                fix = right
                fx_ind = rgt_ind
            else:
                fix = left
                fx_ind = lft_ind
            if fix.get_key() > item.get_key():
                self.heap[ind] = fix
                self.heap[fx_ind] = item
                self.down_heapify(fx_ind)
                return
        elif lft_ind < len(self.heap) :
            left = self.heap[lft_ind]
            if left.get_key() > item.get_key():
                self.heap[ind] = left
                self.heap[lft_ind] = item
                self.down_heapify(lft_ind)
                return
        return

    def print_heap(self, ind=0, gap=0):
        if ind >= len(self.heap) :
            return
        self.print_heap(2*ind+1, gap+3)
        for _ in range(gap):
            print("  ", end = '')
        print(self.heap[ind])
        self.print_heap(2*ind+2, gap+3)
