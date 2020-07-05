import java.util.*;

public class ArrayMap <K, V> extends AbstractMap <K, V> {

    public class ArrayMapEntry <K, V> implements Map.Entry <K, V> {
        K key;
        V value;

        ArrayMapEntry(K key, V val){
            this.key = key;
            this.value = val;
        }

        public K getKey(){
            return this.key;
        }
        public V getValue(){
            return this.value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }

        @Override
        public boolean equals(Object o) {
            if (((ArrayMapEntry)o).getValue() == this.getValue())
                return true;
            return false;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public String toString() {
            return "key=" + key + ", value=" + value ;
        }

    }

    ArrayList<ArrayMapEntry <K, V>> col = new ArrayList<>();

    @Override
    public Set<Entry<K, V>> entrySet() {
        HashSet h = new HashSet();
        Iterator i = col.iterator();
        while (i.hasNext()){
            h.add(i.next());
        }
        return h;
    }

    //daca exista cheia, dar valoarea este diferita, se actualizeaza valoarea
    //altfel se adauga perechea
    public V put(K key, V value) {

        ArrayMapEntry<K, V> a = new ArrayMapEntry<>(key, value);
        int flag = 0;
        for (ArrayMapEntry<K, V> i : col){
            if (i.getKey() == key) {
                i.setValue(value);
                flag = 1;
            }
        }

        if (flag == 0){
            col.add(a);
        }
        return value;
    }

    @Override
    public int size() {
        return col.size();
    }

    @Override
    public boolean containsKey(Object key) {
        for (int i = 0; i < col.size(); i++){
            if (col.get(i).getKey() == (K)key)
                return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        for (int i = 0; i < col.size(); i++){
            if (col.get(i).getKey() == (K)key)
                return col.get(i).getValue();
        }
        return null;
    }
}