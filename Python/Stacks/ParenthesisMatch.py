#!/usr/bin/env python3

"""Check whether the parenthesis are correctly matched in a bracketed expression."""

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

def is_match(expr):
    """Check using a stack whether the brackets are correctly matched"""
    _stack = ArrayStack()
    lefts = '{[('
    rights = '}])'
    for _c in expr:
        if _c in lefts:
            _stack.push(_c)
        elif _c in rights:
            if _stack.is_empty():
                return False
            else:
                if rights.index(_c) != lefts.index(_stack.pop()):
                    return False
    return _stack.is_empty()


def main():
    """Read the expression from terminal and pass it to the function"""
    brackets = sys.argv[1]
    print(is_match(brackets))

if __name__ == '__main__':
    main()
