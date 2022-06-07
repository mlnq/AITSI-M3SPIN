package aitsi.m3spin.commons.interfaces;

import aitsi.m3spin.commons.enums.EntityType;
import aitsi.m3spin.query.model.enums.AttributeEnum;

import java.util.Arrays;

public interface TNode {
    TNode getLeftSibling();

    void setLeftSibling(TNode leftSibling);

    TNode getRightSibling();

    void setRightSibling(TNode rightSibling);

    TNode getParent();

    void setParent(TNode parent);

    TNode getChild();

    void setChild(TNode child);

    EntityType getType();

    default boolean hasAttribute() {
        return Arrays.stream(AttributeEnum.values())
                .anyMatch(attributeEnum -> attributeEnum.getEntityType().equals(getType()));
    }
}
