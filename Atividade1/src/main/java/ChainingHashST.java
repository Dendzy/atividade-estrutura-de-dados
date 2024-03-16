import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.NoSuchElementException;

public class ChainingHashST<Key, Value> {
    private static final int CAPACIDADE_INICIAL = 4;

    private int n;
    private int m;
    private DequeSearch<Key,Value>[] st;

    public ChainingHashST(int m) {
        this.m = m;
        st = (DequeSearch<Key, Value>[]) new DequeSearch[m];
        for(int i = 0; i < m; i++) {
            st[i] = new DequeSearch<Key,Value>();
        }
    }

    public ChainingHashST() {
        this(CAPACIDADE_INICIAL);
    }

    private void resize(int chains) {
        ChainingHashST<Key,Value> temp = new ChainingHashST<Key,Value>(chains);
        for(int i = 0; i < m; i++) {
            for(Key key : st[i].keys()) {
                temp.put(key, st[i].get(key));
            }
        }
        this.m = temp.m;
        this.n = temp.n;
        this.st = st;
    }

    private int hashTextbook(Key key) {
        return (Math.abs(key.hashCode())) % m;
    }

    private int hash(Key key) {
        int h = Math.abs(key.hashCode());
        double ftmp = ((Math.sqrt(5) - 1) / 2) * h;
        int itmp = (int) ftmp;
        ftmp = ftmp - itmp;
        itmp = (int) (ftmp * m);
        return itmp;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Key key) {
        if(key == null) throw new IllegalArgumentException("O argumento para a função get() é nulo");
        return get(key) != null;
    }

    public Value get(Key key) {
        if(key == null) throw new IllegalArgumentException("O argumento para a função get() é nulo");
        int i = hash(key);
        return st[i].get(key);
    }

    public void put(Key key, Value val) {
        if(key == null) throw new IllegalArgumentException("O primeiro argumento para a função put() é nulo");
        if(val == null) {
            delete(key);
            return;
        }

        if(n >= 10 * m) resize(2 * m);
        int i = hash(key);
        if(!st[i].contains(key)) n++;
        st[i].put(key,val);
    }

    public void delete(Key key) {
        if(key == null) throw new IllegalArgumentException("O argumento para a função delete() é nulo");
        int i = hash(key);
        if(st[i].contains(key)) n--;
        st[i].delete(key);

        if(m > CAPACIDADE_INICIAL && n <= 2 * m) resize(m/2);
    }

    public Iterable<Key> keys() {
        Deque<Key> queue = new Deque<Key>();
        for(int i = 0; i < m; i++) {
            for(Key key: st[i].keys()) {
                queue.push_back(key);
            }
        }
        return queue;
    }
}
