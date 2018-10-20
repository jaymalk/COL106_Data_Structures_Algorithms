#!/usr/bin/env python3

import sys

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

def is_matched_html(file):
    """Parse the HTML file from tag to tag to find if there is any error"""
    _stack = ArrayStack()
    j = file.find('<')
    while j != -1:
        k = file.find('>', j+1)
        if k == -1:
            return False
        tag = file[j+1:k]
        if not tag.startswith('/'):
            _stack.push(tag)
        else:
            if _stack.is_empty():
                return False
            if _stack.pop() != tag[1:]:
                return False
        j = file.find('<', k+1)
    return _stack.is_empty()

def make_file(file):
    """Take input a file and make it into one line."""
    _file_string = ""
    for line in file:
        _file_string = _file_string+line.rstrip('\n')
    return _file_string

def main():
    """Take input a HTML file and pass it to the function."""
    file = open(sys.argv[1], "r")
    print(is_matched_html(make_file(file)))

if __name__ == '__main__':
    main()
