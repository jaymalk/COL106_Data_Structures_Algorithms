#!/usr/bin/env python3

"""Implement Queues using Python in-built list. Poping out using its inherent properties."""

class MyQueue:
    """Queues Class."""

    def __init__(self):
        """Initialize the python list. Non-public data."""
        self._list = []

    def __len__(self):
        """Return the size of the Queue."""
        return len(self._list)

    def __str__(self):
        """Queue as string. Listing all it's items."""
        _s = ""
        for item in self._list:
            _s = str(item)+" -> "+_s
        return _s[:-4]

    def is_empty(self):
        """Tell whether the list is empty or not."""
        return len(self) == 0

    def enqueue(self, item):
        """Input an item and put it in the last."""
        self._list.append(item)

    def dequeue(self):
        """Remove and Return the item at the frontself.
           Raise Exception if Queue is empty.
        """
        if self.is_empty():
            raise Exception("Queue is empty.")
        return self._list.pop(0)

    def last(self):
        """Return (but don't remove) the last added item."""
        if self.is_empty():
            raise Exception("Queue is empty.")
        return self._list[-1]

    def front(self):
        """Return (but don't remove) the item on the front."""
        if self.is_empty():
            raise Exception("Queue is empty.")
        return self._list[0]
