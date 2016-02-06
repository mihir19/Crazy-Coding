class Node(object):
    def __init__(self, data=None, node=None):
        self.data = data
        self.node = node

    def get_data(self):
        return self.data

    def get_node(self):
        return self.node

    def next_node(self, new_node):
        self.node = new_node

class Linked_list(object):
    def __init__(self, head=None):
        self.head = head

    def insert(self, data):
        new_node = Node(data)
        new_node.next_node(self.head)
        self.head = new_node

    def get_size(self):
        current = self.head
        count = 0
        while current:
            count+=1
            current = current.get_node()
        return count

    def search(self, data):
        current = self.head
        while current.get_node()==data:
            current = current.get_node()
        if current is None:
            print "Reached end of linked list and no data found!"
        else:
            return current

    def delete_node(self, data):
        current=self.head
        previous = None
        while current.get_node()==data:
            previous = current
            current = current.get_node()
        if current is None:
            print "data not found to be deleted"
        elif previous is None:
            previous = current.get_node()
        else:
            previous.next_node(current.get_node())
