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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart;
import org.obeonetwork.dsl.uml2.properties.uml.parts.UmlViewsRepository;
import org.obeonetwork.dsl.uml2.properties.uml.providers.UmlMessages;


// End of user code

/**
 * @author <a href="mailto:stephane.bouchet@obeo.fr">Stephane Bouchet</a>
 * 
 */
public class InteractionUsePropertiesEditionPartImpl extends CompositePropertiesEditionPart implements ISWTPropertiesEditionPart, InteractionUsePropertiesEditionPart {

	protected Text name;
	protected EMFComboViewer visibility;
	protected ReferencesTable clientDependency;
	protected List<ViewerFilter> clientDependencyBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> clientDependencyFilters = new ArrayList<ViewerFilter>();
	protected ReferencesTable covered;
	protected List<ViewerFilter> coveredBusinessFilters = new ArrayList<ViewerFilter>();
	protected List<ViewerFilter> coveredFilters = new ArrayList<ViewerFilter>();
	protected EObjectFlatComboViewer enclosingInteraction;
	protected EObjectFlatComboViewer enclosingOperand;
	protected EObjectFlatComboViewer refersTo;



	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public InteractionUsePropertiesEditionPartImpl(IPropertiesEditionComponent editionComponent) {
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
		CompositionSequence interactionUseStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = interactionUseStep.addStep(UmlViewsRepository.InteractionUse.Properties.class);
		propertiesStep.addStep(UmlViewsRepository.InteractionUse.Properties.name);
		propertiesStep.addStep(UmlViewsRepository.InteractionUse.Properties.visibility);
		propertiesStep.addStep(UmlViewsRepository.InteractionUse.Properties.clientDependency);
		propertiesStep.addStep(UmlViewsRepository.InteractionUse.Properties.covered);
		propertiesStep.addStep(UmlViewsRepository.InteractionUse.Properties.enclosingInteraction);
		propertiesStep.addStep(UmlViewsRepository.InteractionUse.Properties.enclosingOperand);
		propertiesStep.addStep(UmlViewsRepository.InteractionUse.Properties.refersTo);
		
		
		composer = new PartComposer(interactionUseStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == UmlViewsRepository.InteractionUse.Properties.class) {
					return createPropertiesGroup(parent);
				}
				if (key == UmlViewsRepository.InteractionUse.Properties.name) {
					return createNameText(parent);
				}
				if (key == UmlViewsRepository.InteractionUse.Properties.visibility) {
					return createVisibilityEMFComboViewer(parent);
				}
				if (key == UmlViewsRepository.InteractionUse.Properties.clientDependency) {
					return createClientDependencyAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.InteractionUse.Properties.covered) {
					return createCoveredAdvancedReferencesTable(parent);
				}
				if (key == UmlViewsRepository.InteractionUse.Properties.enclosingInteraction) {
					return createEnclosingInteractionFlatComboViewer(parent);
				}
				if (key == UmlViewsRepository.InteractionUse.Properties.enclosingOperand) {
					return createEnclosingOperandFlatComboViewer(parent);
				}
				if (key == UmlViewsRepository.InteractionUse.Properties.refersTo) {
					return createRefersToFlatComboViewer(parent);
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
		propertiesGroup.setText(UmlMessages.InteractionUsePropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesGroupData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesGroupData.horizontalSpan = 3;
		propertiesGroup.setLayoutData(propertiesGroupData);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		return propertiesGroup;
	}

	
	protected Composite createNameText(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.InteractionUsePropertiesEditionPart_NameLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.name, UmlViewsRepository.SWT_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
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
						propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.name, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, name.getText()));
				}
			}

		});
		EditingUtils.setID(name, UmlViewsRepository.InteractionUse.Properties.name);
		EditingUtils.setEEFtype(name, "eef::Text"); //$NON-NLS-1$
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.InteractionUse.Properties.name, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	
	protected Composite createVisibilityEMFComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.InteractionUsePropertiesEditionPart_VisibilityLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.visibility, UmlViewsRepository.SWT_KIND));
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.visibility, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getVisibility()));
			}

		});
		visibility.setID(UmlViewsRepository.InteractionUse.Properties.visibility);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.InteractionUse.Properties.visibility, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected Composite createClientDependencyAdvancedReferencesTable(Composite parent) {
		this.clientDependency = new ReferencesTable(UmlMessages.InteractionUsePropertiesEditionPart_ClientDependencyLabel, new ReferencesTableListener() {
			public void handleAdd() { addClientDependency(); }
			public void handleEdit(EObject element) { editClientDependency(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveClientDependency(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromClientDependency(element); }
			public void navigateTo(EObject element) { }
		});
		this.clientDependency.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.InteractionUse.Properties.clientDependency, UmlViewsRepository.SWT_KIND));
		this.clientDependency.createControls(parent);
		this.clientDependency.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.clientDependency, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData clientDependencyData = new GridData(GridData.FILL_HORIZONTAL);
		clientDependencyData.horizontalSpan = 3;
		this.clientDependency.setLayoutData(clientDependencyData);
		this.clientDependency.disableMove();
		clientDependency.setID(UmlViewsRepository.InteractionUse.Properties.clientDependency);
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
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.clientDependency,
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
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		clientDependency.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromClientDependency(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.clientDependency, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
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

	/**
	 * 
	 */
	protected Composite createCoveredAdvancedReferencesTable(Composite parent) {
		this.covered = new ReferencesTable(UmlMessages.InteractionUsePropertiesEditionPart_CoveredLabel, new ReferencesTableListener() {
			public void handleAdd() { addCovered(); }
			public void handleEdit(EObject element) { editCovered(element); }
			public void handleMove(EObject element, int oldIndex, int newIndex) { moveCovered(element, oldIndex, newIndex); }
			public void handleRemove(EObject element) { removeFromCovered(element); }
			public void navigateTo(EObject element) { }
		});
		this.covered.setHelpText(propertiesEditionComponent.getHelpContent(UmlViewsRepository.InteractionUse.Properties.covered, UmlViewsRepository.SWT_KIND));
		this.covered.createControls(parent);
		this.covered.addSelectionListener(new SelectionAdapter() {
			
			public void widgetSelected(SelectionEvent e) {
				if (e.item != null && e.item.getData() instanceof EObject) {
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.covered, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SELECTION_CHANGED, null, e.item.getData()));
				}
			}
			
		});
		GridData coveredData = new GridData(GridData.FILL_HORIZONTAL);
		coveredData.horizontalSpan = 3;
		this.covered.setLayoutData(coveredData);
		this.covered.disableMove();
		covered.setID(UmlViewsRepository.InteractionUse.Properties.covered);
		covered.setEEFType("eef::AdvancedReferencesTable"); //$NON-NLS-1$
		return parent;
	}

	/**
	 * 
	 */
	protected void addCovered() {
		TabElementTreeSelectionDialog dialog = new TabElementTreeSelectionDialog(covered.getInput(), coveredFilters, coveredBusinessFilters,
		"covered", propertiesEditionComponent.getEditingContext().getAdapterFactory(), current.eResource()) {
			@Override
			public void process(IStructuredSelection selection) {
				for (Iterator<?> iter = selection.iterator(); iter.hasNext();) {
					EObject elem = (EObject) iter.next();
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.covered,
						PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.ADD, null, elem));
				}
				covered.refresh();
			}
		};
		dialog.open();
	}

	/**
	 * 
	 */
	protected void moveCovered(EObject element, int oldIndex, int newIndex) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.covered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.MOVE, element, newIndex));
		covered.refresh();
	}

	/**
	 * 
	 */
	protected void removeFromCovered(EObject element) {
		propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.covered, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.REMOVE, null, element));
		covered.refresh();
	}

	/**
	 * 
	 */
	protected void editCovered(EObject element) {
		EObjectPropertiesEditionContext context = new EObjectPropertiesEditionContext(propertiesEditionComponent.getEditingContext(), propertiesEditionComponent, element, adapterFactory);
		PropertiesEditingProvider provider = (PropertiesEditingProvider)adapterFactory.adapt(element, PropertiesEditingProvider.class);
		if (provider != null) {
			PropertiesEditingPolicy policy = provider.getPolicy(context);
			if (policy != null) {
				policy.execute();
				covered.refresh();
			}
		}
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createEnclosingInteractionFlatComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.InteractionUsePropertiesEditionPart_EnclosingInteractionLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.enclosingInteraction, UmlViewsRepository.SWT_KIND));
		enclosingInteraction = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.enclosingInteraction, UmlViewsRepository.SWT_KIND));
		enclosingInteraction.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		enclosingInteraction.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.enclosingInteraction, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getEnclosingInteraction()));
			}

		});
		GridData enclosingInteractionData = new GridData(GridData.FILL_HORIZONTAL);
		enclosingInteraction.setLayoutData(enclosingInteractionData);
		enclosingInteraction.setID(UmlViewsRepository.InteractionUse.Properties.enclosingInteraction);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.InteractionUse.Properties.enclosingInteraction, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createEnclosingOperandFlatComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.InteractionUsePropertiesEditionPart_EnclosingOperandLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.enclosingOperand, UmlViewsRepository.SWT_KIND));
		enclosingOperand = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.enclosingOperand, UmlViewsRepository.SWT_KIND));
		enclosingOperand.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		enclosingOperand.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.enclosingOperand, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getEnclosingOperand()));
			}

		});
		GridData enclosingOperandData = new GridData(GridData.FILL_HORIZONTAL);
		enclosingOperand.setLayoutData(enclosingOperandData);
		enclosingOperand.setID(UmlViewsRepository.InteractionUse.Properties.enclosingOperand);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.InteractionUse.Properties.enclosingOperand, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * 
	 */
	protected Composite createRefersToFlatComboViewer(Composite parent) {
		SWTUtils.createPartLabel(parent, UmlMessages.InteractionUsePropertiesEditionPart_RefersToLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.refersTo, UmlViewsRepository.SWT_KIND));
		refersTo = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.InteractionUse.Properties.refersTo, UmlViewsRepository.SWT_KIND));
		refersTo.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));

		refersTo.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {
				propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(InteractionUsePropertiesEditionPartImpl.this, UmlViewsRepository.InteractionUse.Properties.refersTo, PropertiesEditionEvent.CHANGE, PropertiesEditionEvent.SET, null, getRefersTo()));
			}

		});
		GridData refersToData = new GridData(GridData.FILL_HORIZONTAL);
		refersTo.setLayoutData(refersToData);
		refersTo.setID(UmlViewsRepository.InteractionUse.Properties.refersTo);
		SWTUtils.createHelpButton(parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.InteractionUse.Properties.refersTo, UmlViewsRepository.SWT_KIND), null); //$NON-NLS-1$
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
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#getName()
	 * 
	 */
	public String getName() {
		return name.getText();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setName(String newValue)
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
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#getVisibility()
	 * 
	 */
	public Enumerator getVisibility() {
		EEnumLiteral selection = (EEnumLiteral) ((StructuredSelection) visibility.getSelection()).getFirstElement();
		return selection.getInstance();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#initVisibility(EEnum eenum, Enumerator current)
	 */
	public void initVisibility(EEnum eenum, Enumerator current) {
		visibility.setInput(eenum.getELiterals());
		visibility.modelUpdating(new StructuredSelection(current));
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setVisibility(Enumerator newValue)
	 * 
	 */
	public void setVisibility(Enumerator newValue) {
		visibility.modelUpdating(new StructuredSelection(newValue));
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#initClientDependency(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
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
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#updateClientDependency()
	 * 
	 */
	public void updateClientDependency() {
	clientDependency.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addFilterClientDependency(ViewerFilter filter)
	 * 
	 */
	public void addFilterToClientDependency(ViewerFilter filter) {
		clientDependencyFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addBusinessFilterClientDependency(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToClientDependency(ViewerFilter filter) {
		clientDependencyBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#isContainedInClientDependencyTable(EObject element)
	 * 
	 */
	public boolean isContainedInClientDependencyTable(EObject element) {
		return ((ReferencesTableSettings)clientDependency.getInput()).contains(element);
	}




	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#initCovered(org.eclipse.emf.eef.runtime.ui.widgets.referencestable.ReferencesTableSettings)
	 */
	public void initCovered(ReferencesTableSettings settings) {
		if (current.eResource() != null && current.eResource().getResourceSet() != null)
			this.resourceSet = current.eResource().getResourceSet();
		ReferencesTableContentProvider contentProvider = new ReferencesTableContentProvider();
		covered.setContentProvider(contentProvider);
		covered.setInput(settings);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#updateCovered()
	 * 
	 */
	public void updateCovered() {
	covered.refresh();
}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addFilterCovered(ViewerFilter filter)
	 * 
	 */
	public void addFilterToCovered(ViewerFilter filter) {
		coveredFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addBusinessFilterCovered(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToCovered(ViewerFilter filter) {
		coveredBusinessFilters.add(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#isContainedInCoveredTable(EObject element)
	 * 
	 */
	public boolean isContainedInCoveredTable(EObject element) {
		return ((ReferencesTableSettings)covered.getInput()).contains(element);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#getEnclosingInteraction()
	 * 
	 */
	public EObject getEnclosingInteraction() {
		if (enclosingInteraction.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) enclosingInteraction.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#initEnclosingInteraction(EObjectFlatComboSettings)
	 */
	public void initEnclosingInteraction(EObjectFlatComboSettings settings) {
		enclosingInteraction.setInput(settings);
		if (current != null) {
			enclosingInteraction.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setEnclosingInteraction(EObject newValue)
	 * 
	 */
	public void setEnclosingInteraction(EObject newValue) {
		if (newValue != null) {
			enclosingInteraction.setSelection(new StructuredSelection(newValue));
		} else {
			enclosingInteraction.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setEnclosingInteractionButtonMode(ButtonsModeEnum newValue)
	 */
	public void setEnclosingInteractionButtonMode(ButtonsModeEnum newValue) {
		enclosingInteraction.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addFilterEnclosingInteraction(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEnclosingInteraction(ViewerFilter filter) {
		enclosingInteraction.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addBusinessFilterEnclosingInteraction(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEnclosingInteraction(ViewerFilter filter) {
		enclosingInteraction.addBusinessRuleFilter(filter);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#getEnclosingOperand()
	 * 
	 */
	public EObject getEnclosingOperand() {
		if (enclosingOperand.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) enclosingOperand.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#initEnclosingOperand(EObjectFlatComboSettings)
	 */
	public void initEnclosingOperand(EObjectFlatComboSettings settings) {
		enclosingOperand.setInput(settings);
		if (current != null) {
			enclosingOperand.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setEnclosingOperand(EObject newValue)
	 * 
	 */
	public void setEnclosingOperand(EObject newValue) {
		if (newValue != null) {
			enclosingOperand.setSelection(new StructuredSelection(newValue));
		} else {
			enclosingOperand.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setEnclosingOperandButtonMode(ButtonsModeEnum newValue)
	 */
	public void setEnclosingOperandButtonMode(ButtonsModeEnum newValue) {
		enclosingOperand.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addFilterEnclosingOperand(ViewerFilter filter)
	 * 
	 */
	public void addFilterToEnclosingOperand(ViewerFilter filter) {
		enclosingOperand.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addBusinessFilterEnclosingOperand(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToEnclosingOperand(ViewerFilter filter) {
		enclosingOperand.addBusinessRuleFilter(filter);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#getRefersTo()
	 * 
	 */
	public EObject getRefersTo() {
		if (refersTo.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) refersTo.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#initRefersTo(EObjectFlatComboSettings)
	 */
	public void initRefersTo(EObjectFlatComboSettings settings) {
		refersTo.setInput(settings);
		if (current != null) {
			refersTo.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setRefersTo(EObject newValue)
	 * 
	 */
	public void setRefersTo(EObject newValue) {
		if (newValue != null) {
			refersTo.setSelection(new StructuredSelection(newValue));
		} else {
			refersTo.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#setRefersToButtonMode(ButtonsModeEnum newValue)
	 */
	public void setRefersToButtonMode(ButtonsModeEnum newValue) {
		refersTo.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addFilterRefersTo(ViewerFilter filter)
	 * 
	 */
	public void addFilterToRefersTo(ViewerFilter filter) {
		refersTo.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.InteractionUsePropertiesEditionPart#addBusinessFilterRefersTo(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToRefersTo(ViewerFilter filter) {
		refersTo.addBusinessRuleFilter(filter);
	}







	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return UmlMessages.InteractionUse_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
