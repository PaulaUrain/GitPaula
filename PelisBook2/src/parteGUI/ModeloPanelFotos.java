package parteGUI;

import java.util.ArrayList;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import logicaPrograma.Foto;

public class ModeloPanelFotos implements ListModel<Foto> {
	ArrayList<Foto> paraPrueba;
	ArrayList<ListDataListener> listeners = new ArrayList<ListDataListener>();

	public ModeloPanelFotos() {
		paraPrueba = new ArrayList<Foto>();
	}

	public void add(Foto a) {
		this.paraPrueba.add(a);
		avisarAnyadido(paraPrueba.size() - 1);
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return paraPrueba.size();
	}

	@Override
	public Foto getElementAt(int index) {
		return paraPrueba.get(index);

	}

	public void setElementAt(int index, Foto element) {
		paraPrueba.set(index, element);
		avisarAnyadido(index);
	}

	public void removeElementAt(int index) {
		paraPrueba.remove(index);
		avisarAnyadido(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		// TODO Auto-generated method stub
		listeners.add(l);
		System.out.println("listener");
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	public void avisarAnyadido(int posi) {
		for (ListDataListener ldl : listeners) {
			System.out.println("cambio");
			if (paraPrueba.size() == 0) {
				ldl.intervalAdded(new ListDataEvent(this,
						ListDataEvent.INTERVAL_ADDED, 0, paraPrueba.size()));
			} else {
				ldl.intervalAdded(new ListDataEvent(this,
						ListDataEvent.INTERVAL_ADDED, 0,
						paraPrueba.size() - 1));
			}
		}
	}
}
