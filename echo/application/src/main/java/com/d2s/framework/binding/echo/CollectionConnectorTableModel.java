/*
 * Copyright (c) 2005 Design2see. All rights reserved.
 */
package com.d2s.framework.binding.echo;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nextapp.echo2.app.table.AbstractTableModel;

import com.d2s.framework.binding.ConnectorValueChangeEvent;
import com.d2s.framework.binding.ICollectionConnector;
import com.d2s.framework.binding.ICompositeValueConnector;
import com.d2s.framework.binding.IConnectorValueChangeListener;
import com.d2s.framework.binding.IRenderableCompositeValueConnector;
import com.d2s.framework.binding.IValueConnector;
import com.d2s.framework.util.Coordinates;

/**
 * This class implements a table model backed by a collection connector. As
 * expected, this table model will fire necessary events depending on connectors
 * received events. Its column are determined by the prototype connector which
 * serves as model for the table rows.
 * <p>
 * Copyright 2005 Design2See. All rights reserved.
 * <p>
 *
 * @version $LastChangedRevision$
 * @author Vincent Vandenschrick
 */
public class CollectionConnectorTableModel extends AbstractTableModel {

  private static final long serialVersionUID = -5216602907606895000L;

  private ICollectionConnector                        collectionConnector;
  private Map<Integer, IConnectorValueChangeListener> cachedRowListeners;
  private Map<Coordinates, CellConnectorListener>     cachedCellListeners;
  private List<String>                                columnConnectorKeys;
  private Map<String, Class>                          columnClassesByIds;

  /**
   * Constructs a new <code>CollectionConnectorTableModel</code> instance.
   *
   * @param collectionConnector
   *          the collection connector holding the values of this table model.
   * @param columnConnectorKeys
   *          the list of column connector ids.
   */
  public CollectionConnectorTableModel(
      ICollectionConnector collectionConnector, List<String> columnConnectorKeys) {
    super();
    this.collectionConnector = collectionConnector;
    this.columnConnectorKeys = columnConnectorKeys;
    bindConnector();
  }

  /**
   * Returns the backed collection connector size.
   * <p>
   * {@inheritDoc}
   */
  public int getRowCount() {
    return collectionConnector.getChildConnectorCount();
  }

  /**
   * Returns the size of the child connectors prototype used to model the rows.
   * <p>
   * {@inheritDoc}
   */
  public int getColumnCount() {
    return columnConnectorKeys.size();
  }

  /**
   * Returns the value of the connector which backs the cell model (2nd level of
   * nesting).
   * <p>
   * {@inheritDoc}
   */
  public Object getValueAt(int rowIndex, int columnIndex) {
    IValueConnector cellConnector = getConnectorAt(rowIndex, columnIndex);
    if (cellConnector instanceof ICompositeValueConnector) {
      return cellConnector.toString();
    }
    Object connectorValue = cellConnector.getConnectorValue();
    if (connectorValue instanceof byte[]) {
      return null;
    }
    return connectorValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    if (columnClassesByIds != null) {
      return columnClassesByIds.get(columnConnectorKeys.get(columnIndex));
    }
    return super.getColumnClass(columnIndex);
  }

  private IValueConnector getConnectorAt(int rowIndex, int columnIndex) {
    return ((ICompositeValueConnector) collectionConnector
        .getChildConnector(rowIndex)).getChildConnector(columnConnectorKeys
        .get(columnIndex));
  }

  private void bindConnector() {
    collectionConnector
        .addConnectorValueChangeListener(new TableConnectorListener());
    for (int row = 0; row < collectionConnector.getChildConnectorCount(); row++) {
      bindChildRowConnector(row);
    }
  }

  private void bindChildRowConnector(int row) {
    ICompositeValueConnector rowConnector = (ICompositeValueConnector) collectionConnector
        .getChildConnector(row);
    if (rowConnector != null) {
      rowConnector
          .addConnectorValueChangeListener(getChildRowConnectorListener(row));
      for (int col = 0; col < columnConnectorKeys.size(); col++) {
        IValueConnector cellConnector = rowConnector
            .getChildConnector(columnConnectorKeys.get(col));
        if (cellConnector instanceof IRenderableCompositeValueConnector
            && ((IRenderableCompositeValueConnector) cellConnector)
                .getRenderingConnector() != null) {
          ((IRenderableCompositeValueConnector) cellConnector)
              .getRenderingConnector().addConnectorValueChangeListener(
                  getChildCellConnectorListener(row, col));
        } else {
          CellConnectorListener listener = getChildCellConnectorListener(row,
              col);
          cellConnector.addConnectorValueChangeListener(listener);
          cellConnector.addPropertyChangeListener(listener);
        }
      }
    }
  }

  private IConnectorValueChangeListener getChildRowConnectorListener(int row) {
    if (cachedRowListeners == null) {
      cachedRowListeners = new HashMap<Integer, IConnectorValueChangeListener>();
    }
    IConnectorValueChangeListener cachedListener = cachedRowListeners
        .get(new Integer(row));
    if (cachedListener == null) {
      cachedListener = new RowConnectorListener(row);
      cachedRowListeners.put(new Integer(row), cachedListener);
    }
    return cachedListener;
  }

  private CellConnectorListener getChildCellConnectorListener(int row, int col) {
    if (cachedCellListeners == null) {
      cachedCellListeners = new HashMap<Coordinates, CellConnectorListener>();
    }
    CellConnectorListener cachedListener = cachedCellListeners
        .get(new Coordinates(row, col));
    if (cachedListener == null) {
      cachedListener = new CellConnectorListener(row, col);
      cachedCellListeners.put(new Coordinates(row, col), cachedListener);
    }
    return cachedListener;
  }

  private class TableConnectorListener implements IConnectorValueChangeListener {

    /**
     * {@inheritDoc}
     */
    public void connectorValueChange(final ConnectorValueChangeEvent evt) {
      Collection<?> oldCollection = null;
      if (evt.getOldValue() instanceof Collection) {
        oldCollection = (Collection<?>) evt.getOldValue();
      }
      Collection<?> newCollection = (Collection<?>) evt.getNewValue();
      int oldCollectionSize = 0;
      int newCollectionSize = 0;
      if (oldCollection != null) {
        oldCollectionSize = oldCollection.size();
      }
      if (newCollection != null) {
        newCollectionSize = newCollection.size();
      }
      if (newCollectionSize > oldCollectionSize) {
        fireTableRowsInserted(oldCollectionSize, newCollectionSize - 1);
        for (int row = oldCollectionSize; row < newCollectionSize; row++) {
          bindChildRowConnector(row);
        }
      } else if (newCollectionSize < oldCollectionSize) {
        fireTableRowsDeleted(newCollectionSize, oldCollectionSize - 1);
      }
      if (evt.getNewValue() != null
          && !((Collection<?>) evt.getNewValue()).isEmpty()) {
        collectionConnector.setSelectedIndices(new int[] {0});
      }
    }
  }

  private final class RowConnectorListener implements
      IConnectorValueChangeListener {

    private int row;

    private RowConnectorListener(int row) {
      this.row = row;
    }

    /**
     * {@inheritDoc}
     */
    public void connectorValueChange(@SuppressWarnings("unused")
    ConnectorValueChangeEvent evt) {
      if (row < getRowCount()) {
        fireTableRowsUpdated(row, row);
      }
      if (collectionConnector.getSelectedIndices() != null) {
        if (Arrays.binarySearch(collectionConnector.getSelectedIndices(), row) >= 0) {
          collectionConnector.setSelectedIndices(new int[0]);
        }
      }
    }
  }

  private final class CellConnectorListener implements
      IConnectorValueChangeListener, PropertyChangeListener {

    private Coordinates cell;

    private CellConnectorListener(int row, int col) {
      cell = new Coordinates(row, col);
    }

    /**
     * {@inheritDoc}
     */
    public void connectorValueChange(@SuppressWarnings("unused")
    ConnectorValueChangeEvent evt) {
      updateCell();
    }

    /**
     * {@inheritDoc}
     */
    public void propertyChange(@SuppressWarnings("unused")
    PropertyChangeEvent evt) {
      updateCell();
    }

    private void updateCell() {
      if (cell.getX() < getRowCount()) {
        fireTableCellUpdated(cell.getX(), cell.getY());
      }
    }
  }

  /**
   * Sets the columnClassesByIds.
   *
   * @param columnClassesByIds
   *          the columnClassesByIds to set.
   */
  public void setColumnClassesByIds(Map<String, Class> columnClassesByIds) {
    this.columnClassesByIds = columnClassesByIds;
  }
}
