package edu.mci.fooddirector.model.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractEntityTest {

    private AbstractEntityImpl entity;

    @BeforeEach
    void setUp() {
        entity = new AbstractEntityImpl();
    }

    @Test
    void getId() {
        entity.setId(1L);
        assertEquals(1L, entity.getId());
    }

    @Test
    void setId() {
        entity.setId(1L);
        assertEquals(1L, entity.getId());
    }

    @Test
    void testHashCodeWithId() {
        entity.setId(1L);
        int expectedHashCode = Long.valueOf(1L).hashCode();
        assertEquals(expectedHashCode, entity.hashCode());
    }

    @Test
    void testHashCodeWithoutId() {
        int expectedHashCode = System.identityHashCode(entity);
        assertEquals(expectedHashCode, entity.hashCode());
    }

    @Test
    void testEqualsWithSameId() {
        entity.setId(1L);
        AbstractEntityImpl otherEntity = new AbstractEntityImpl();
        otherEntity.setId(1L);
        assertTrue(entity.equals(otherEntity));
    }

    @Test
    void testEqualsWithDifferentId() {
        entity.setId(1L);
        AbstractEntityImpl otherEntity = new AbstractEntityImpl();
        otherEntity.setId(2L);
        assertFalse(entity.equals(otherEntity));
    }

    @Test
    void testEqualsWithoutId() {
        AbstractEntityImpl otherEntity = new AbstractEntityImpl();
        assertFalse(entity.equals(otherEntity));
    }

    @Test
    void testEqualsWithNull() {
        assertFalse(entity.equals(null));
    }

    @Test
    void testEqualsWithDifferentClass() {
        assertFalse(entity.equals("string"));
    }

    private static class AbstractEntityImpl extends AbstractEntity {
    }
}
