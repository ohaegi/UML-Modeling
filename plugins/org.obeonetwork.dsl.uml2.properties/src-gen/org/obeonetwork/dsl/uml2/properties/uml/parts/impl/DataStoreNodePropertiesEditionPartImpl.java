/*******************************************************************************
 * Copyright (c) 2009, 2011 Obeo.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/
package org.obeonetwork.dsl.uml2.properties.uml.parts.impl;

// Start of user code for imports
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.context.impl.EObjectPropertiesEditionContext;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.policies.PropertiesEditingPolicy;
import org.eclipse.emf.eef.runtime.providers.PropertiesEditingProvider;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;
import org.eclipse.emf.eef.runtime.ui.utils.EditingUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.EMFComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.EObjectFlatComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable;
import org.eclipse.emf.eef.runtime.ui.widgets.ReferencesTable.ReferencesTableListener;
import org.eclipse.emf.eef.runtime.ui.widgets.SWTUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.TabElementTreeSelectionDialog;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableContentProvider;
import org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart;
import org.obeonetwork.dsl.uml2.properties.uml.parts.UmlViewsRepository;
import org.obeonetwork.dsl.uml2.properties.uml.providers.UmlMessages;


// End of user code

/**
 * @author <a href="mailto:stephane.bouchet@obeo.fr">Stephane Bouchet</a>
 * 
 */
public class DataStoreNodePropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, DataStoreNodePropertiesEditionPart {

	protected Text name;
	protected EMFComboViewer visibility;
	protected ReferencesTable clientDependency;
	protected List<ViewerFilter> clientDependencyBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> clientDependencyFilters = new ArrayList<ViewerFilter>();
	protected Button isLeaf;
	protected EObjectFlatComboViewer inStructuredNode;
	protected EObjectFlatComboViewer activity;
	protected ReferencesTable outgoing;
	protected List<ViewerFilter> outgoingBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> outgoingFilters = new ArrayList<ViewerFilter>();
	protected ReferencesTable incoming;
	protected List<ViewerFilter> incomingBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> incomingFilters = new ArrayList<ViewerFilter>();
	protected ReferencesTable inPartition;
	protected List<ViewerFilter> inPartitionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> inPartitionFilters = new ArrayList<ViewerFilter>();
	protected ReferencesTable inInterruptibleRegion;
	protected List<ViewerFilter> inInterruptibleRegionBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> inInterruptibleRegionFilters = new ArrayList<ViewerFilter>();
	protected ReferencesTable redefinedNode;
	protected List<ViewerFilter> redefinedNodeBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> redefinedNodeFilters = new ArrayList<ViewerFilter>();
	protected EObjectFlatComboViewer type;
	protected EMFComboViewer ordering;
	protected Button isControlType;
	protected ReferencesTable inState;
	protected List<ViewerFilter> inStateBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> inStateFilters = new ArrayList<ViewerFilter>();
	protected EObjectFlatComboViewer selection;



	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public DataStoreNodePropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart#
	 * 			createFigure(org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public Composite createFigure(final Composite parent) {
		view = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(view);
		return view;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.ISWTPropertiesEditionPart#
	 * 			createControls(org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public void createControls(Composite view) { 
		CompositionSequence dataStoreNodeStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = dataStoreNodeStep.addStep(UmlViewsRepository.DataStoreNode.Properties.class);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.name);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.visibility);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.clientDependency);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.isLeaf);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.inStructuredNode);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.activity);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.outgoing);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.incoming);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.inPartition);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.redefinedNode);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.type);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.ordering);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.isControlType);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.inState);
		propertiesStep.addStep(UmlViewsRepository.DataStoreNode.Properties.selection);
		
		
		composer = new PartComposer(dataStoreNodeStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == UmlViewsRepository.DataStoreNode.Properties.class) {
					return createPropertiesGroup(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.name) {
					return createNameText(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.visibility) {
					return createVisibilityEMFComboViewer(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.clientDependency) {
					return createClientDependencyAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.isLeaf) {
					return createIsLeafCheckbox(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.inStructuredNode) {
					return createInStructuredNodeFlatComboViewer(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.activity) {
					return createActivityFlatComboViewer(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.outgoing) {
					return createOutgoingAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.incoming) {
					return createIncomingAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.inPartition) {
					return createInPartitionAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion) {
					return createInInterruptibleRegionAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.redefinedNode) {
					return createRedefinedNodeAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.type) {
					return createTypeFlatComboViewer(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.ordering) {
					return createOrderingEMFComboViewer(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.isControlType) {
					return createIsControlTypeCheckbox(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.inState) {
					return createInStateAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.DataStoreNode.Properties.selection) {
					return createSelectionFlatComboViewer(parent);
				}
				return parent;
			}
		};
		composer.compose(view);
	}

	/**
	 * 
	 */
	protected Composite createPropertiesGroup(Composite parent) {
		Group propertiesGroup = new Group(parent, SWT.NONE);
		propertiesGroup.setText(UmlMessages.DataStoreNodePropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		return propertiesGroup;
	}

	
	protected Composite createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.DataStoreNodePropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.name, UmlViewsRepository.SWT_KIND));
		name = new Text(parent, SWT.BORDER);
		GridData nameData = new GridData(GridData.FILL_HORIZONTAL);
		name.setLayoutData(nameData);
		name.addFocusListener(new FocusAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.FocusAdapter#focusLost(org.eclipse.swt.events.FocusEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void focusLost(FocusEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
			}

		});
		name.addKeyListener(new KeyAdapter() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.swt.events.KeyAdapter#keyPressed(org.eclipse.swt.events.KeyEvent)
			 * 
			 */
			@Override
			@SuppressWarnings("synthetic-access")
			public void keyPressed(KeyEvent e) {
				if (e.character == SWT.CR) {
					if (propertiesEditionComponent != null)
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		EditingUtils.setID(name, UmlViewsRepository.DataStoreNode.Properties.name);
		EditingUtils.setEEFtype(name, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.name, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	
	protected Composite createVisibilityEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.DataStoreNodePropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.visibility, UmlViewsRepository.SWT_KIND));
		visibility = new EMFComboViewer(parent);
		visibility.setContentProvider(new ArrayContentProvider());
		visibility.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData visibilityData = new GridData(GridData.FILL_HORIZONTAL);
		visibility.getCombo().setLayoutData(visibilityData);
		visibility.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 * 	
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.visibility, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getVisibility()));
			}

		});
		visibility.setID(UmlViewsRepository.DataStoreNode.Properties.visibility);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.visibility, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createClientDependencyAdvancedReferencesTable(Composite parent) {
		this.clientDependency = new ReferencesTable(UmlMessages.DataStoreNodePropertiesEditionPart_ClientDependencyLabel, new ReferencesTableListener() {
			public void handleAdd() { addClientDependency(); }
			public void handleEdit(EObject element) { editClientDependency(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveClientDependency(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromClientDependency(element); }
			public void navigateTo(EObject element) { }
		});
		this.clientDependency.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.clientDependency, UmlViewsRepository.SWT_KIND));
		this.clientDependency.createControls(parent);
		this.clientDependency.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.clientDependency, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData clientDependencyData = new GridData(GridData.FILL_HORIZONTAL);
		clientDependencyData.horizontalSpan = 3;
		this.clientDependency.setLayoutData(clientDependencyData);
		this.clientDependency.disableMove();
		clientDependency.setID(UmlViewsRepository.DataStoreNode.Properties.clientDependency);
		clientDependency.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addClientDependency() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(clientDependency.getInput(), clientDependencyFilters, clientDependencyBusinessFilters,
		"clientDependency", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.clientDependency,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				clientDependency.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveClientDependency(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		clientDependency.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromClientDependency(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		clientDependency.refresh();
	}

	/**
	 * 
	 */
	protected void editClientDependency(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				clientDependency.refresh();
			}
		}
	}

	
	protected Composite createIsLeafCheckbox(Composite parent) {
		isLeaf = new Button(parent, SWT.CHECK);
		isLeaf.setText(UmlMessages.DataStoreNodePropertiesEditionPart_IsLeafLabel);
		isLeaf.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.isLeaf, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isLeaf.getSelection())));
			}

		});
		GridData isLeafData = new GridData(GridData.FILL_HORIZONTAL);
		isLeafData.horizontalSpan = 2;
		isLeaf.setLayoutData(isLeafData);
		EditingUtils.setID(isLeaf, UmlViewsRepository.DataStoreNode.Properties.isLeaf);
		EditingUtils.setEEFtype(isLeaf, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.isLeaf, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createInStructuredNodeFlatComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.DataStoreNodePropertiesEditionPart_InStructuredNodeLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.inStructuredNode, UmlViewsRepository.SWT_KIND));
		inStructuredNode = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.inStructuredNode, UmlViewsRepository.SWT_KIND));
		inStructuredNode.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		inStructuredNode.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inStructuredNode, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getInStructuredNode()));
			}

		});
		GridData inStructuredNodeData = new GridData(GridData.FILL_HORIZONTAL);
		inStructuredNode.setLayoutData(inStructuredNodeData);
		inStructuredNode.setID(UmlViewsRepository.DataStoreNode.Properties.inStructuredNode);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.inStructuredNode, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createActivityFlatComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.DataStoreNodePropertiesEditionPart_ActivityLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.activity, UmlViewsRepository.SWT_KIND));
		activity = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.activity, UmlViewsRepository.SWT_KIND));
		activity.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		activity.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.activity, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getActivity()));
			}

		});
		GridData activityData = new GridData(GridData.FILL_HORIZONTAL);
		activity.setLayoutData(activityData);
		activity.setID(UmlViewsRepository.DataStoreNode.Properties.activity);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.activity, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createOutgoingAdvancedReferencesTable(Composite parent) {
		this.outgoing = new ReferencesTable(UmlMessages.DataStoreNodePropertiesEditionPart_OutgoingLabel, new ReferencesTableListener() {
			public void handleAdd() { addOutgoing(); }
			public void handleEdit(EObject element) { editOutgoing(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveOutgoing(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromOutgoing(element); }
			public void navigateTo(EObject element) { }
		});
		this.outgoing.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.outgoing, UmlViewsRepository.SWT_KIND));
		this.outgoing.createControls(parent);
		this.outgoing.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.outgoing, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData outgoingData = new GridData(GridData.FILL_HORIZONTAL);
		outgoingData.horizontalSpan = 3;
		this.outgoing.setLayoutData(outgoingData);
		this.outgoing.disableMove();
		outgoing.setID(UmlViewsRepository.DataStoreNode.Properties.outgoing);
		outgoing.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addOutgoing() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(outgoing.getInput(), outgoingFilters, outgoingBusinessFilters,
		"outgoing", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.outgoing,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				outgoing.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveOutgoing(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.outgoing, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		outgoing.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromOutgoing(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.outgoing, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		outgoing.refresh();
	}

	/**
	 * 
	 */
	protected void editOutgoing(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				outgoing.refresh();
			}
		}
	}

	/**
	 * 
	 */
	protected Composite createIncomingAdvancedReferencesTable(Composite parent) {
		this.incoming = new ReferencesTable(UmlMessages.DataStoreNodePropertiesEditionPart_IncomingLabel, new ReferencesTableListener() {
			public void handleAdd() { addIncoming(); }
			public void handleEdit(EObject element) { editIncoming(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveIncoming(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromIncoming(element); }
			public void navigateTo(EObject element) { }
		});
		this.incoming.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.incoming, UmlViewsRepository.SWT_KIND));
		this.incoming.createControls(parent);
		this.incoming.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.incoming, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData incomingData = new GridData(GridData.FILL_HORIZONTAL);
		incomingData.horizontalSpan = 3;
		this.incoming.setLayoutData(incomingData);
		this.incoming.disableMove();
		incoming.setID(UmlViewsRepository.DataStoreNode.Properties.incoming);
		incoming.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addIncoming() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(incoming.getInput(), incomingFilters, incomingBusinessFilters,
		"incoming", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.incoming,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				incoming.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveIncoming(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.incoming, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		incoming.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromIncoming(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.incoming, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		incoming.refresh();
	}

	/**
	 * 
	 */
	protected void editIncoming(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				incoming.refresh();
			}
		}
	}

	/**
	 * 
	 */
	protected Composite createInPartitionAdvancedReferencesTable(Composite parent) {
		this.inPartition = new ReferencesTable(UmlMessages.DataStoreNodePropertiesEditionPart_InPartitionLabel, new ReferencesTableListener() {
			public void handleAdd() { addInPartition(); }
			public void handleEdit(EObject element) { editInPartition(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveInPartition(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromInPartition(element); }
			public void navigateTo(EObject element) { }
		});
		this.inPartition.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.inPartition, UmlViewsRepository.SWT_KIND));
		this.inPartition.createControls(parent);
		this.inPartition.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inPartition, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData inPartitionData = new GridData(GridData.FILL_HORIZONTAL);
		inPartitionData.horizontalSpan = 3;
		this.inPartition.setLayoutData(inPartitionData);
		this.inPartition.disableMove();
		inPartition.setID(UmlViewsRepository.DataStoreNode.Properties.inPartition);
		inPartition.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addInPartition() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(inPartition.getInput(), inPartitionFilters, inPartitionBusinessFilters,
		"inPartition", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inPartition,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				inPartition.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveInPartition(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inPartition, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		inPartition.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromInPartition(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inPartition, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		inPartition.refresh();
	}

	/**
	 * 
	 */
	protected void editInPartition(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				inPartition.refresh();
			}
		}
	}

	/**
	 * 
	 */
	protected Composite createInInterruptibleRegionAdvancedReferencesTable(Composite parent) {
		this.inInterruptibleRegion = new ReferencesTable(UmlMessages.DataStoreNodePropertiesEditionPart_InInterruptibleRegionLabel, new ReferencesTableListener() {
			public void handleAdd() { addInInterruptibleRegion(); }
			public void handleEdit(EObject element) { editInInterruptibleRegion(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveInInterruptibleRegion(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromInInterruptibleRegion(element); }
			public void navigateTo(EObject element) { }
		});
		this.inInterruptibleRegion.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion, UmlViewsRepository.SWT_KIND));
		this.inInterruptibleRegion.createControls(parent);
		this.inInterruptibleRegion.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData inInterruptibleRegionData = new GridData(GridData.FILL_HORIZONTAL);
		inInterruptibleRegionData.horizontalSpan = 3;
		this.inInterruptibleRegion.setLayoutData(inInterruptibleRegionData);
		this.inInterruptibleRegion.disableMove();
		inInterruptibleRegion.setID(UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion);
		inInterruptibleRegion.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addInInterruptibleRegion() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(inInterruptibleRegion.getInput(), inInterruptibleRegionFilters, inInterruptibleRegionBusinessFilters,
		"inInterruptibleRegion", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				inInterruptibleRegion.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveInInterruptibleRegion(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		inInterruptibleRegion.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromInInterruptibleRegion(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inInterruptibleRegion, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		inInterruptibleRegion.refresh();
	}

	/**
	 * 
	 */
	protected void editInInterruptibleRegion(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				inInterruptibleRegion.refresh();
			}
		}
	}

	/**
	 * 
	 */
	protected Composite createRedefinedNodeAdvancedReferencesTable(Composite parent) {
		this.redefinedNode = new ReferencesTable(UmlMessages.DataStoreNodePropertiesEditionPart_RedefinedNodeLabel, new ReferencesTableListener() {
			public void handleAdd() { addRedefinedNode(); }
			public void handleEdit(EObject element) { editRedefinedNode(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveRedefinedNode(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromRedefinedNode(element); }
			public void navigateTo(EObject element) { }
		});
		this.redefinedNode.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.redefinedNode, UmlViewsRepository.SWT_KIND));
		this.redefinedNode.createControls(parent);
		this.redefinedNode.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.redefinedNode, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData redefinedNodeData = new GridData(GridData.FILL_HORIZONTAL);
		redefinedNodeData.horizontalSpan = 3;
		this.redefinedNode.setLayoutData(redefinedNodeData);
		this.redefinedNode.disableMove();
		redefinedNode.setID(UmlViewsRepository.DataStoreNode.Properties.redefinedNode);
		redefinedNode.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addRedefinedNode() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(redefinedNode.getInput(), redefinedNodeFilters, redefinedNodeBusinessFilters,
		"redefinedNode", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.redefinedNode,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				redefinedNode.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveRedefinedNode(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.redefinedNode, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		redefinedNode.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromRedefinedNode(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.redefinedNode, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		redefinedNode.refresh();
	}

	/**
	 * 
	 */
	protected void editRedefinedNode(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				redefinedNode.refresh();
			}
		}
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createTypeFlatComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.DataStoreNodePropertiesEditionPart_TypeLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.type, UmlViewsRepository.SWT_KIND));
		type = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.type, UmlViewsRepository.SWT_KIND));
		type.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		type.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.type, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getType()));
			}

		});
		GridData typeData = new GridData(GridData.FILL_HORIZONTAL);
		type.setLayoutData(typeData);
		type.setID(UmlViewsRepository.DataStoreNode.Properties.type);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.type, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	
	protected Composite createOrderingEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.DataStoreNodePropertiesEditionPart_OrderingLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.ordering, UmlViewsRepository.SWT_KIND));
		ordering = new EMFComboViewer(parent);
		ordering.setContentProvider(new ArrayContentProvider());
		ordering.setLabelProvider(new AdapterFactoryLabelProvider(new EcoreAdapterFactory()));
		GridData orderingData = new GridData(GridData.FILL_HORIZONTAL);
		ordering.getCombo().setLayoutData(orderingData);
		ordering.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 * 	
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.ordering, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getOrdering()));
			}

		});
		ordering.setID(UmlViewsRepository.DataStoreNode.Properties.ordering);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.ordering, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	
	protected Composite createIsControlTypeCheckbox(Composite parent) {
		isControlType = new Button(parent, SWT.CHECK);
		isControlType.setText(UmlMessages.DataStoreNodePropertiesEditionPart_IsControlTypeLabel);
		isControlType.addSelectionListener(new SelectionAdapter() {

			/**
			 * {@inheritDoc}
			 *
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 * 	
			 */
			public void widgetSelected(SelectionEvent e) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.isControlType, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, new Boolean(isControlType.getSelection())));
			}

		});
		GridData isControlTypeData = new GridData(GridData.FILL_HORIZONTAL);
		isControlTypeData.horizontalSpan = 2;
		isControlType.setLayoutData(isControlTypeData);
		EditingUtils.setID(isControlType, UmlViewsRepository.DataStoreNode.Properties.isControlType);
		EditingUtils.setEEFtype(isControlType, "eef::Checkbox"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.isControlType, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createInStateAdvancedReferencesTable(Composite parent) {
		this.inState = new ReferencesTable(UmlMessages.DataStoreNodePropertiesEditionPart_InStateLabel, new ReferencesTableListener() {
			public void handleAdd() { addInState(); }
			public void handleEdit(EObject element) { editInState(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveInState(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromInState(element); }
			public void navigateTo(EObject element) { }
		});
		this.inState.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.inState, UmlViewsRepository.SWT_KIND));
		this.inState.createControls(parent);
		this.inState.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inState, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData inStateData = new GridData(GridData.FILL_HORIZONTAL);
		inStateData.horizontalSpan = 3;
		this.inState.setLayoutData(inStateData);
		this.inState.disableMove();
		inState.setID(UmlViewsRepository.DataStoreNode.Properties.inState);
		inState.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addInState() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(inState.getInput(), inStateFilters, inStateBusinessFilters,
		"inState", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inState,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				inState.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveInState(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inState, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		inState.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromInState(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.inState, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		inState.refresh();
	}

	/**
	 * 
	 */
	protected void editInState(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				inState.refresh();
			}
		}
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createSelectionFlatComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.DataStoreNodePropertiesEditionPart_SelectionLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.selection, UmlViewsRepository.SWT_KIND));
		selection = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.DataStoreNode.Properties.selection, UmlViewsRepository.SWT_KIND));
		selection.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		selection.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(DataStoreNodePropertiesEditionPartImpl.this, UmlViewsRepository.DataStoreNode.Properties.selection, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getSelection()));
			}

		});
		GridData selectionData = new GridData(GridData.FILL_HORIZONTAL);
		selection.setLayoutData(selectionData);
		selection.setID(UmlViewsRepository.DataStoreNode.Properties.selection);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.DataStoreNode.Properties.selection, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}



	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionListener#firePropertiesChanged(org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent)
	 * 
	 */
	public void firePropertiesChanged(IPropertiesEditionEvent event) {
		// Start of user code for tab synchronization

// End of user code
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getName()
	 * 
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setName(String newValue)
	 * 
	 */
	public void setName(String newValue) {
		if (newValue != null) {
			name.setText(newValue);
		} else {
			name.setText(""); //$NON-NLS-1$
		}
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getVisibility()
	 * 
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setVisibility(Enumerator newValue)
	 * 
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initClientDependency(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initClientDependency(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		clientDependency.setContentProvider(contentProvider);
		clientDependency.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#updateClientDependency()
	 * 
	 */
	public void updateClientDependency() {
	clientDependency.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterClientDependency(ViewerFilter filter)
	 * 
	 */
	public void addFilterToClientDependency(ViewerFilter filter) {
		clientDependencyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterClientDependency(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToClientDependency(ViewerFilter filter) {
		clientDependencyBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#isContainedInClientDependencyTable(EObject element)
	 * 
	 */
	public boolean isContainedInClientDependencyTable(EObject element) {
		return ((ReferencesTableSettings)clientDependency.getInput()).contains(element);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getIsLeaf()
	 * 
	 */
	public Boolean getIsLeaf() {
		return Boolean.valueOf(isLeaf.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setIsLeaf(Boolean newValue)
	 * 
	 */
	public void setIsLeaf(Boolean newValue) {
		if (newValue != null) {
			isLeaf.setSelection(newValue.booleanValue());
		} else {
			isLeaf.setSelection(false);
		}
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getInStructuredNode()
	 * 
	 */
	public EObject getInStructuredNode() {
		if (inStructuredNode.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) inStructuredNode.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initInStructuredNode(EObjectFlatComboSettings)
	 */
	public void initInStructuredNode(EObjectFlatComboSettings settings) {
		inStructuredNode.setInput(settings);
		if (current != null) {
			inStructuredNode.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setInStructuredNode(EObject newValue)
	 * 
	 */
	public void setInStructuredNode(EObject newValue) {
		if (newValue != null) {
			inStructuredNode.setSelection(new StructuredSelection(newValue));
		} else {
			inStructuredNode.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setInStructuredNodeButtonMode(ButtonsModeEnum newValue)
	 */
	public void setInStructuredNodeButtonMode(ButtonsModeEnum newValue) {
		inStructuredNode.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterInStructuredNode(ViewerFilter filter)
	 * 
	 */
	public void addFilterToInStructuredNode(ViewerFilter filter) {
		inStructuredNode.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterInStructuredNode(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToInStructuredNode(ViewerFilter filter) {
		inStructuredNode.addBusinessRuleFilter(filter);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getActivity()
	 * 
	 */
	public EObject getActivity() {
		if (activity.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) activity.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initActivity(EObjectFlatComboSettings)
	 */
	public void initActivity(EObjectFlatComboSettings settings) {
		activity.setInput(settings);
		if (current != null) {
			activity.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setActivity(EObject newValue)
	 * 
	 */
	public void setActivity(EObject newValue) {
		if (newValue != null) {
			activity.setSelection(new StructuredSelection(newValue));
		} else {
			activity.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setActivityButtonMode(ButtonsModeEnum newValue)
	 */
	public void setActivityButtonMode(ButtonsModeEnum newValue) {
		activity.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterActivity(ViewerFilter filter)
	 * 
	 */
	public void addFilterToActivity(ViewerFilter filter) {
		activity.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterActivity(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToActivity(ViewerFilter filter) {
		activity.addBusinessRuleFilter(filter);
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initOutgoing(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initOutgoing(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		outgoing.setContentProvider(contentProvider);
		outgoing.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#updateOutgoing()
	 * 
	 */
	public void updateOutgoing() {
	outgoing.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterOutgoing(ViewerFilter filter)
	 * 
	 */
	public void addFilterToOutgoing(ViewerFilter filter) {
		outgoingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterOutgoing(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToOutgoing(ViewerFilter filter) {
		outgoingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#isContainedInOutgoingTable(EObject element)
	 * 
	 */
	public boolean isContainedInOutgoingTable(EObject element) {
		return ((ReferencesTableSettings)outgoing.getInput()).contains(element);
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initIncoming(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initIncoming(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		incoming.setContentProvider(contentProvider);
		incoming.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#updateIncoming()
	 * 
	 */
	public void updateIncoming() {
	incoming.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterIncoming(ViewerFilter filter)
	 * 
	 */
	public void addFilterToIncoming(ViewerFilter filter) {
		incomingFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterIncoming(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToIncoming(ViewerFilter filter) {
		incomingBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#isContainedInIncomingTable(EObject element)
	 * 
	 */
	public boolean isContainedInIncomingTable(EObject element) {
		return ((ReferencesTableSettings)incoming.getInput()).contains(element);
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initInPartition(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initInPartition(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		inPartition.setContentProvider(contentProvider);
		inPartition.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#updateInPartition()
	 * 
	 */
	public void updateInPartition() {
	inPartition.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterInPartition(ViewerFilter filter)
	 * 
	 */
	public void addFilterToInPartition(ViewerFilter filter) {
		inPartitionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterInPartition(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToInPartition(ViewerFilter filter) {
		inPartitionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#isContainedInInPartitionTable(EObject element)
	 * 
	 */
	public boolean isContainedInInPartitionTable(EObject element) {
		return ((ReferencesTableSettings)inPartition.getInput()).contains(element);
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initInInterruptibleRegion(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initInInterruptibleRegion(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		inInterruptibleRegion.setContentProvider(contentProvider);
		inInterruptibleRegion.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#updateInInterruptibleRegion()
	 * 
	 */
	public void updateInInterruptibleRegion() {
	inInterruptibleRegion.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterInInterruptibleRegion(ViewerFilter filter)
	 * 
	 */
	public void addFilterToInInterruptibleRegion(ViewerFilter filter) {
		inInterruptibleRegionFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterInInterruptibleRegion(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToInInterruptibleRegion(ViewerFilter filter) {
		inInterruptibleRegionBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#isContainedInInInterruptibleRegionTable(EObject element)
	 * 
	 */
	public boolean isContainedInInInterruptibleRegionTable(EObject element) {
		return ((ReferencesTableSettings)inInterruptibleRegion.getInput()).contains(element);
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initRedefinedNode(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initRedefinedNode(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		redefinedNode.setContentProvider(contentProvider);
		redefinedNode.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#updateRedefinedNode()
	 * 
	 */
	public void updateRedefinedNode() {
	redefinedNode.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterRedefinedNode(ViewerFilter filter)
	 * 
	 */
	public void addFilterToRedefinedNode(ViewerFilter filter) {
		redefinedNodeFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterRedefinedNode(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToRedefinedNode(ViewerFilter filter) {
		redefinedNodeBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#isContainedInRedefinedNodeTable(EObject element)
	 * 
	 */
	public boolean isContainedInRedefinedNodeTable(EObject element) {
		return ((ReferencesTableSettings)redefinedNode.getInput()).contains(element);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getType()
	 * 
	 */
	public EObject getType() {
		if (type.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) type.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initType(EObjectFlatComboSettings)
	 */
	public void initType(EObjectFlatComboSettings settings) {
		type.setInput(settings);
		if (current != null) {
			type.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setType(EObject newValue)
	 * 
	 */
	public void setType(EObject newValue) {
		if (newValue != null) {
			type.setSelection(new StructuredSelection(newValue));
		} else {
			type.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setTypeButtonMode(ButtonsModeEnum newValue)
	 */
	public void setTypeButtonMode(ButtonsModeEnum newValue) {
		type.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterType(ViewerFilter filter)
	 * 
	 */
	public void addFilterToType(ViewerFilter filter) {
		type.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterType(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToType(ViewerFilter filter) {
		type.addBusinessRuleFilter(filter);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getOrdering()
	 * 
	 */
	public Enumerator getOrdering() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) ordering.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initOrdering(EEnum eenum, Enumerator current)
	 */
	public void initOrdering(EEnum eenum, Enumerator current) {
		ordering.setInput(eenum.getELiterals());
		ordering.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setOrdering(Enumerator newValue)
	 * 
	 */
	public void setOrdering(Enumerator newValue) {
		ordering.modelUpdating(new StructuredSelection(newValue));
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getIsControlType()
	 * 
	 */
	public Boolean getIsControlType() {
		return Boolean.valueOf(isControlType.getSelection());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setIsControlType(Boolean newValue)
	 * 
	 */
	public void setIsControlType(Boolean newValue) {
		if (newValue != null) {
			isControlType.setSelection(newValue.booleanValue());
		} else {
			isControlType.setSelection(false);
		}
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initInState(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initInState(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		inState.setContentProvider(contentProvider);
		inState.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#updateInState()
	 * 
	 */
	public void updateInState() {
	inState.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterInState(ViewerFilter filter)
	 * 
	 */
	public void addFilterToInState(ViewerFilter filter) {
		inStateFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterInState(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToInState(ViewerFilter filter) {
		inStateBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#isContainedInInStateTable(EObject element)
	 * 
	 */
	public boolean isContainedInInStateTable(EObject element) {
		return ((ReferencesTableSettings)inState.getInput()).contains(element);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#getSelection()
	 * 
	 */
	public EObject getSelection() {
		if (selection.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) selection.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#initSelection(EObjectFlatComboSettings)
	 */
	public void initSelection(EObjectFlatComboSettings settings) {
		selection.setInput(settings);
		if (current != null) {
			selection.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setSelection(EObject newValue)
	 * 
	 */
	public void setSelection(EObject newValue) {
		if (newValue != null) {
			selection.setSelection(new StructuredSelection(newValue));
		} else {
			selection.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#setSelectionButtonMode(ButtonsModeEnum newValue)
	 */
	public void setSelectionButtonMode(ButtonsModeEnum newValue) {
		selection.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addFilterSelection(ViewerFilter filter)
	 * 
	 */
	public void addFilterToSelection(ViewerFilter filter) {
		selection.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.DataStoreNodePropertiesEditionPart#addBusinessFilterSelection(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToSelection(ViewerFilter filter) {
		selection.addBusinessRuleFilter(filter);
	}







	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return UmlMessages.DataStoreNode_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
