#!/usr/bin/env python3

class ArrayStack:
    """LIFO Stack implementation using in-built Python-List for storage"""

    def __init__(self):
        """Create an empty stack."""
        self._data = []             # nonpublic list instance

    def __len__(self):
        """Return the number of elements in the stack."""
        return len(self._data)

    def __str__(self):
        """Print the items of the stack from top to bottom."""
        max_len = 0
        _s = ""
        for item in self._data:
            if len(str(item)) > max_len:
                max_len = len(str(item))
        for i in range(-1, -1-len(self), -1):
            item = self._data[i]
            _s += ('| '+str(item)+' '*(max_len-len(str(item)))+' |'+'\n')
        _s += ('='*(max_len+4))
        return _s

    def is_empty(self):
        """Return True if stack is empty."""
        return len(self) == 0

    def push(self, e):
        """Add element e to the top of the stack."""
        self._data.append(e)

    def top(self):
        """Return (but do not remove) the element at the top of the stack.
           Raise 'Empty' exception if stack is empty.
        """
        if self.is_empty():
            raise Exception("Stack is Empty.")
        return self._data[-1]

    def pop(self):
        """Remove and return the element at the top of the stack.
           Raise 'Empty' exception is stack is empty.
        """
        if self.is_empty():
            raise Exception("Stack is Empty.")
        return self._data.pop()
