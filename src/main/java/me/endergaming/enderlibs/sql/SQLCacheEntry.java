package me.endergaming.enderlibs.sql;

import java.util.Arrays;
import java.util.Objects;

class SQLCacheEntry {

    private final Object[] params;

    public SQLCacheEntry(Object[] params) {
        this.params = params;
    }

    public Object[] getParams() {
        return this.params;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.params);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof SQLCacheEntry)) {
            return false;
        }
        return Arrays.equals(this.params, ((SQLCacheEntry) o).params);
    }

}
