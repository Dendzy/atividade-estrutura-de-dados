import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DequeSearch<Key, Item> implements Iterable<Item> {
    private int n;
    private No Sentinela;

    public DequeSearch() {
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }

    private class No{
        private Item dado;
        private Key chave;
        private No prox;
        private No ant;
    }

    public void push_front(Key key, Item item) {
        No temp = new No();
        temp.dado = item;
        temp.chave = key;

        temp.ant = Sentinela;
        temp.prox = Sentinela.prox;

        Sentinela.prox = temp;
        temp.prox.ant = temp;
        n++;
    }

    public void push_back(Key key, Item item) {
        No temp = new No();
        temp.dado = item;
        temp.chave = key;

        temp.prox = Sentinela;
        temp.ant = Sentinela.ant;

        Sentinela.ant = temp;
        temp.ant.prox = temp;
        n++;
    }

    public boolean contains(Key key) {
        if(key == null) throw new IllegalArgumentException("Argumento para contains() é nulo!");
        return get(key) != null;
    }

    public Item get(Key key) {
        if(key == null) throw new IllegalArgumentException("Argumento para get() é nulo!");
        for(No x = Sentinela.prox; x != Sentinela; x = x.prox) {
            if(key.equals(x.chave)) {
                return x.dado;
            }
        }
        return null;
    }

    public void delete(Key key) {
        if(key == null) throw new IllegalArgumentException("Argumento para delete() é nulo!");
        delete(Sentinela.prox, key);
    }

    private void remove(No temp) {
        temp.ant.prox = temp.prox;
        temp.prox.ant = temp.ant;
        n--;
    }

    private void delete(No x, Key key) {
        if (x == Sentinela) return;
        if(key.equals(x.chave)) {
            remove(x);
            return;
        }
        delete(x.prox,key);
    }

    public void put(Key key, Item val) {
        if(key == null) throw new IllegalArgumentException("Primeiro Argumento para put() é nulo!");
        if(val == null) {
            delete(key);
            return;
        }
        for(No x = Sentinela.prox; x != Sentinela; x = x.prox) {
            if(key.equals(x.chave)) {
                x.dado = val;
                return;
            }
        }
        push_front(key,val);
    }

    public Item pop_front() {
        No temp = Sentinela.prox;
        Item meuDado = temp.dado;

        temp.prox.ant = temp.ant;
        temp.ant.prox = temp.prox;
        n--;
        return meuDado;
    }

    public Item pop_back() {
        No temp = Sentinela.ant;
        Item meuDado = temp.dado;

        temp.prox.ant = temp.ant;
        temp.ant.prox = temp.prox;
        n--;
        return meuDado;
    }

    public No first() {
        if(Sentinela == Sentinela.prox) return null;
        return Sentinela.prox;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public ListIterator<Item> iterator() {
        return new DequeIterator();
    }

    public class DequeIterator implements ListIterator<Item> {
        private No atual = Sentinela.prox;
        private int indice = 0;
        private No acessadoultimo = null;

        @Override
        public boolean hasNext() {
            return indice < (n);
        }

        @Override
        public boolean hasPrevious() {
            return indice > 0;
        }

        @Override
        public int previousIndex() {
            return indice - 1;
        }

        @Override
        public int nextIndex() {
            return indice;
        }

        @Override
        public Item next() {
            if(!hasNext()) return null;

            Item meuDado = atual.dado;
            acessadoultimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;
        }

        @Override
        public Item previous() {
            if(!hasPrevious()) return null;
            atual = atual.ant;

            Item meuDado = atual.dado;
            acessadoultimo = atual;
            indice--;
            return meuDado;
        }

        public Item get() {
            if(atual == null) throw new IllegalStateException();
            return atual.dado;
        }

        @Override
        public void set(Item x) {
            if(acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.dado = x;
        }

        @Override
        public void remove() {
            if(acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.ant.prox = acessadoultimo.prox;
            acessadoultimo.prox.ant = acessadoultimo.ant;
            n--;
            if(atual == acessadoultimo) {
                atual = acessadoultimo.prox;
            } else {
                indice--;
                acessadoultimo = null;
            }
        }

        @Override
        public void add(Item x) {
            No temp = new No();
            temp.dado = x;

            temp.prox = atual.prox;
            temp.ant = atual;

            temp.prox.ant = temp;
            atual.prox = temp;
            n++;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(Item item: this) {
            s.append(item + " ");
        }
        return s.toString();
    }

    public Iterable<Key> keys() {
        Deque<Key> queue = new Deque<Key>();
        for(No x = Sentinela.prox; x != Sentinela; x = x.prox) {
            queue.push_back(x.chave);
        }
        return queue;
    }
}
