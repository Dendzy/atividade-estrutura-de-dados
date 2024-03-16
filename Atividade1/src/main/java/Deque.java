import java.util.ListIterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int n;
    private No Sentinela;

    private class No {
        private Item dado;
        private No prox;
        private No ant;
    }

    public Deque() {
        n = 0;
        Sentinela = new No();
        Sentinela.prox = Sentinela;
        Sentinela.ant = Sentinela;
    }

    public void push_front(Item item) {
        No temp = new No();
        temp.dado = item;

        temp.ant = Sentinela;
        temp.prox = Sentinela;

        Sentinela.prox = temp;
        temp.prox.ant = temp;
        n++;
    }

    public void push_back(Item item) {
        No temp = new No();
        temp.dado = item;

        temp.ant = Sentinela;
        temp.prox = Sentinela;

        Sentinela.ant = temp;
        temp.ant.prox = temp;
        n++;
    }

    public Item pop_front() {
        No temp = Sentinela.prox;
        Item meuDado = temp.dado;

        temp.ant.prox = temp.prox;
        temp.prox.ant = temp.ant;
        n--;
        return meuDado;
    }

    public Item pop_back() {
        No temp = Sentinela.ant;
        Item meuDado = temp.dado;

        temp.ant.prox = temp.prox;
        temp.prox.ant = temp.ant;
        n--;
        return meuDado;
    }

    public No first() {
        if (Sentinela == Sentinela.prox) return null;
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
            if (!hasNext()) return null;

            Item meuDado = atual.dado;
            acessadoultimo = atual;
            atual = atual.prox;
            indice++;
            return meuDado;
        }

        @Override
        public Item previous() {
            if (!hasNext()) return null;
            atual = atual.ant;

            Item meuDado = atual.dado;
            acessadoultimo = atual;
            indice--;
            return meuDado;
        }


        public Item get() {
            if (atual == null) throw new IllegalStateException();
            return atual.dado;
        }

        @Override
        public void set(Item x) {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.dado = x;
        }

        @Override
        public void remove() {
            if (acessadoultimo == null) throw new IllegalStateException();
            acessadoultimo.ant.prox = acessadoultimo.prox;
            acessadoultimo.prox.ant = acessadoultimo.ant;
            n--;
            if (atual == acessadoultimo) {
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

            temp.ant.prox = temp;
            atual.prox = temp;
            n++;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString();
    }
}
