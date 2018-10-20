#!/usr/bin/env python3

"""Take input a file and reverse its content line by line (in the same file)."""

import sys

class ArrayStack:
    """LIFO Stack implementation using in-built Python-List for storage"""

    def __init__(self):
        """Create an empty stack."""
        self._data = []             # nonpublic list instance

    def __len__(self):
        """Return the number of elements in the stack."""
        return len(self._data)

    def is_empty(self):
        """Return True if stack is empty."""
        return len(self) == 0

    def push(self, _e):
        """Add element e to the top of the stack."""
        self._data.append(_e)

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


def reverse_file(filename):
    """Open the file (return if no such file), and write it in reverse order"""
    _stack = ArrayStack()
    try:
        _file = open(filename, "r")
    except:
        print("File with name "+filename+" doesn't exist.")
        return
    for line in _file:
        _stack.push(line.rstrip('\n'))
    _file.close()
    _reversed_file = open(filename, "w")
    while not _stack.is_empty():
        _reversed_file.write(_stack.pop()+'\n')


def main():
    """Take input the filename from the terminal and pass it to the function"""
    filename = sys.argv[1]
    reverse_file(filename)

if __name__ == '__main__':
    main()
