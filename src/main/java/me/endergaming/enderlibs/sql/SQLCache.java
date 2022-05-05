package me.endergaming.enderlibs.sql;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

/**
 * A cache to store the values in a single column of a single SQL table
 */
public class SQLCache {

    private final String tableName;
    private final String columnName;
    private final String[] primaryKeyNames;
    private final String deleteQuery;
    private final String selectQuery;
    private final String updateQuery;
    private final Map<SQLCacheEntry, Object> cache = new HashMap<>();
    private final Set<SQLCacheEntry> modified = new HashSet<>();
    private final SQLHelper sql;

    protected SQLCache(SQLHelper sql, String tableName, String columnName, String... primaryKeyNames) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.primaryKeyNames = primaryKeyNames;
        this.deleteQuery = "DELETE FROM " + this.tableName + " WHERE " + this.repeat(primaryKeyNames, " = ?", " AND ");
        this.selectQuery = "SELECT " + columnName + " FROM " + this.tableName + " WHERE " + this.repeat(primaryKeyNames, " = ?", " AND ");
        this.updateQuery = "UPDATE " + this.tableName + " SET " + columnName + " = ? WHERE " + this.repeat(primaryKeyNames, " = ?", " AND ");
        this.sql = sql;
    }

    private String repeat(String[] values, String str, String delimeter) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            builder.append(values[i]).append(str);
            if (i != values.length - 1) {
                builder.append(delimeter);
            }
        }
        return builder.toString();
    }

    /**
     * @return The name of the table this SQLCache is for
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * @return The name of the column this SQLCache is for
     */
    public String getColumnName() {
        return this.columnName;
    }

    /**
     * @return The names of the primary keys used to access and mutate the column this SQLCache is for
     */
    public String[] getPrimaryKeyNames() {
        return this.primaryKeyNames;
    }

    protected boolean keyNamesMatch(String[] matches) {
        for (String match : matches) {
            if (match.equals(this.columnName)) {
                return true;
            }
        }
        for (String key : this.primaryKeyNames) {
            for (String match : matches) {
                if (key.equals(match)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void checkKeys(Object... primaryKeys) {
        if (primaryKeys.length != this.primaryKeyNames.length) {
            throw new IllegalArgumentException("Expected " + this.primaryKeyNames.length + " primary keys, got " + primaryKeys.length);
        }
    }

    /**
     * Deletes a row from the table by its primary keys, and removes it from the cache.
     * This operation will always use a query.
     *
     * @param primaryKeys The keys to use to delete the row
     */
    public void delete(Object... primaryKeys) {
        this.remove(primaryKeys);
        this.sql.execute(this.deleteQuery, primaryKeys);
    }

    /**
     * Removes a cached value, but does not affect the table
     *
     * @param primaryKeys The keys used to access the value
     */
    public void remove(Object... primaryKeys) {
        this.checkKeys(primaryKeys);
        SQLCacheEntry entry = new SQLCacheEntry(primaryKeys);
        this.modified.remove(entry);
        this.cache.remove(entry);
    }

    /**
     * Updates the cached value for a row
     *
     * @param value The value to cache
     * @param primaryKeys The primary keys used to mutate the row
     */
    public void update(Object value, Object... primaryKeys) {
        this.checkKeys(primaryKeys);
        SQLCacheEntry entry = new SQLCacheEntry(primaryKeys);
        if (!this.cache.containsKey(entry)) {
            return;
        }
        this.cache.remove(entry);
        this.modified.add(entry);
        this.cache.put(entry, value);
    }

    /**
     * Gets the cached value for a row, or queries it if it has not been cached yet
     *
     * @param primaryKeys The primary keys used to access the row
     * @param <T> The type of the value
     * @return The value
     */
    public <T> T select(Object... primaryKeys) {
        return (T) this.select(o -> this.sql.querySingleResult(this.selectQuery, primaryKeys), primaryKeys);
    }

    /**
     * Gets the cached value for a String row, or queries it if it has not been cached yet
     *
     * @param primaryKeys The primary keys used to access the row
     * @return The String value
     */
    public String selectString(Object... primaryKeys) {
        return (String) this.select(o -> this.sql.querySingleResultString(this.selectQuery, primaryKeys), primaryKeys);
    }

    /**
     * Gets the cached value for a Long row, or queries it if it has not been cached yet
     *
     * @param primaryKeys The primary keys used to access the row
     * @return The Long value
     */
    public Long selectLong(Object... primaryKeys) {
        return (Long) this.select(o -> this.sql.querySingleResultLong(this.selectQuery, primaryKeys), primaryKeys);
    }

    /**
     * Checks whether a value has been cached by its primary keys
     *
     * @param primaryKeys The primary keys used to access the row
     * @return Whether the value has been cached
     */
    public boolean isCached(Object... primaryKeys) {
        return this.cache.containsKey(new SQLCacheEntry(primaryKeys));
    }

    private Object select(Function<Object[], ?> supplier, Object... primaryKeys) {
        this.checkKeys(primaryKeys);
        SQLCacheEntry entry = new SQLCacheEntry(primaryKeys);
        Object value;
        if (!this.cache.containsKey(entry)) {
            value = supplier.apply(primaryKeys);
            this.cache.put(entry, value);
        } else {
            value = this.cache.get(entry);
        }
        return value;
    }

    /**
     * Clears the cache. WARNING: This will revert all changes that have not been flushed!
     * No updates performed through {@link SQLCache#update(Object, Object...)} will be committed!
     */
    public void clear() {
        this.cache.clear();
    }

    /**
     * Flushes the cache, saving all changes that were made.
     */
    public void flush() {
        this.modified.forEach(s -> {
            Object val = this.cache.get(s);
            Object[] objs = new Object[s.getParams().length + 1];
            objs[0] = val;
            for (int i = 0; i < s.getParams().length; i++) {
                objs[i + 1] = s.getParams()[i];
            }
            this.sql.execute(this.updateQuery, objs);
        });
        this.modified.clear();
    }

    /**
     * Flushes a single value from the cache, saving changes that were made to it
     *
     * @param primaryKeys The primary keys used to access the row
     */
    public void flush(Object... primaryKeys) {
        SQLCacheEntry entry = new SQLCacheEntry(primaryKeys);
        Object val = this.cache.get(entry);
        if (val == null) {
            return;
        }
        Object[] objs = new Object[entry.getParams().length + 1];
        objs[0] = val;
        for (int i = 0; i < entry.getParams().length; i++) {
            objs[i + 1] = entry.getParams()[i];
        }
        this.sql.execute(this.updateQuery, objs);
        this.modified.remove(entry);
    }

}
