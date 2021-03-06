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
package org.obeonetwork.dsl.uml2.properties.uml.parts.forms;

// Start of user code for imports
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.eef.runtime.api.component.IPropertiesEditionComponent;
import org.eclipse.emf.eef.runtime.api.notify.IPropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart;
import org.eclipse.emf.eef.runtime.impl.notify.PropertiesEditionEvent;
import org.eclipse.emf.eef.runtime.impl.parts.CompositePropertiesEditionPart;
import org.eclipse.emf.eef.runtime.ui.parts.PartComposer;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.BindingCompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionSequence;
import org.eclipse.emf.eef.runtime.ui.parts.sequence.CompositionStep;
import org.eclipse.emf.eef.runtime.ui.widgets.ButtonsModeEnum;
import org.eclipse.emf.eef.runtime.ui.widgets.EObjectFlatComboViewer;
import org.eclipse.emf.eef.runtime.ui.widgets.FormUtils;
import org.eclipse.emf.eef.runtime.ui.widgets.eobjflatcombo.EObjectFlatComboSettings;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart;
import org.obeonetwork.dsl.uml2.properties.uml.parts.UmlViewsRepository;
import org.obeonetwork.dsl.uml2.properties.uml.providers.UmlMessages;


// End of user code

/**
 * @author <a href="mailto:stephane.bouchet@obeo.fr">Stephane Bouchet</a>
 * 
 */
public class PackageMergePropertiesEditionPartForm extends CompositePropertiesEditionPart implements IFormPropertiesEditionPart, PackageMergePropertiesEditionPart {

	protected EObjectFlatComboViewer mergedPackage;
	protected EObjectFlatComboViewer receivingPackage;



	/**
	 * Default constructor
	 * @param editionComponent the {@link IPropertiesEditionComponent} that manage this part
	 * 
	 */
	public PackageMergePropertiesEditionPartForm(IPropertiesEditionComponent editionComponent) {
		super(editionComponent);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 *  createFigure(org.eclipse.swt.widgets.Composite, org.eclipse.ui.forms.widgets.FormToolkit)
	 * 
	 */
	public Composite createFigure(final Composite parent, final FormToolkit widgetFactory) {
		ScrolledForm scrolledForm = widgetFactory.createScrolledForm(parent);
		Form form = scrolledForm.getForm();
		view = form.getBody();
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		view.setLayout(layout);
		createControls(widgetFactory, view);
		return scrolledForm;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.emf.eef.runtime.api.parts.IFormPropertiesEditionPart#
	 *  createControls(org.eclipse.ui.forms.widgets.FormToolkit, org.eclipse.swt.widgets.Composite)
	 * 
	 */
	public void createControls(final FormToolkit widgetFactory, Composite view) {
		CompositionSequence packageMergeStep = new BindingCompositionSequence(propertiesEditionComponent);
		CompositionStep propertiesStep = packageMergeStep.addStep(UmlViewsRepository.PackageMerge.Properties.class);
		propertiesStep.addStep(UmlViewsRepository.PackageMerge.Properties.mergedPackage);
		propertiesStep.addStep(UmlViewsRepository.PackageMerge.Properties.receivingPackage);
		
		
		composer = new PartComposer(packageMergeStep) {

			@Override
			public Composite addToPart(Composite parent, Object key) {
				if (key == UmlViewsRepository.PackageMerge.Properties.class) {
					return createPropertiesGroup(widgetFactory, parent);
				}
				if (key == UmlViewsRepository.PackageMerge.Properties.mergedPackage) {
					return createMergedPackageFlatComboViewer(parent, widgetFactory);
				}
				if (key == UmlViewsRepository.PackageMerge.Properties.receivingPackage) {
					return createReceivingPackageFlatComboViewer(parent, widgetFactory);
				}
				return parent;
			}
		};
		composer.compose(view);
	}
	/**
	 * 
	 */
	protected Composite createPropertiesGroup(FormToolkit widgetFactory, final Composite parent) {
		Section propertiesSection = widgetFactory.createSection(parent, Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);
		propertiesSection.setText(UmlMessages.PackageMergePropertiesEditionPart_PropertiesGroupLabel);
		GridData propertiesSectionData = new GridData(GridData.FILL_HORIZONTAL);
		propertiesSectionData.horizontalSpan = 3;
		propertiesSection.setLayoutData(propertiesSectionData);
		Composite propertiesGroup = widgetFactory.createComposite(propertiesSection);
		GridLayout propertiesGroupLayout = new GridLayout();
		propertiesGroupLayout.numColumns = 3;
		propertiesGroup.setLayout(propertiesGroupLayout);
		propertiesSection.setClient(propertiesGroup);
		return propertiesGroup;
	}

	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createMergedPackageFlatComboViewer(Composite parent, FormToolkit widgetFactory) {
		FormUtils.createPartLabel(widgetFactory, parent, UmlMessages.PackageMergePropertiesEditionPart_MergedPackageLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.PackageMerge.Properties.mergedPackage, UmlViewsRepository.FORM_KIND));
		mergedPackage = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.PackageMerge.Properties.mergedPackage, UmlViewsRepository.FORM_KIND));
		widgetFactory.adapt(mergedPackage);
		mergedPackage.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		GridData mergedPackageData = new GridData(GridData.FILL_HORIZONTAL);
		mergedPackage.setLayoutData(mergedPackageData);
		mergedPackage.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(PackageMergePropertiesEditionPartForm.this, UmlViewsRepository.PackageMerge.Properties.mergedPackage, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getMergedPackage()));
			}

		});
		mergedPackage.setID(UmlViewsRepository.PackageMerge.Properties.mergedPackage);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.PackageMerge.Properties.mergedPackage, UmlViewsRepository.FORM_KIND), null); //$NON-NLS-1$
		return parent;
	}

	/**
	 * @param parent the parent composite
	 * @param widgetFactory factory to use to instanciante widget of the form
	 * 
	 */
	protected Composite createReceivingPackageFlatComboViewer(Composite parent, FormToolkit widgetFactory) {
		FormUtils.createPartLabel(widgetFactory, parent, UmlMessages.PackageMergePropertiesEditionPart_ReceivingPackageLabel, propertiesEditionComponent.isRequired(UmlViewsRepository.PackageMerge.Properties.receivingPackage, UmlViewsRepository.FORM_KIND));
		receivingPackage = new EObjectFlatComboViewer(parent, !propertiesEditionComponent.isRequired(UmlViewsRepository.PackageMerge.Properties.receivingPackage, UmlViewsRepository.FORM_KIND));
		widgetFactory.adapt(receivingPackage);
		receivingPackage.setLabelProvider(new AdapterFactoryLabelProvider(adapterFactory));
		GridData receivingPackageData = new GridData(GridData.FILL_HORIZONTAL);
		receivingPackage.setLayoutData(receivingPackageData);
		receivingPackage.addSelectionChangedListener(new ISelectionChangedListener() {

			/**
			 * {@inheritDoc}
			 * 
			 * @see org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(org.eclipse.jface.viewers.SelectionChangedEvent)
			 */
			public void selectionChanged(SelectionChangedEvent event) {
				if (propertiesEditionComponent != null)
					propertiesEditionComponent.firePropertiesChanged(new PropertiesEditionEvent(PackageMergePropertiesEditionPartForm.this, UmlViewsRepository.PackageMerge.Properties.receivingPackage, PropertiesEditionEvent.COMMIT, PropertiesEditionEvent.SET, null, getReceivingPackage()));
			}

		});
		receivingPackage.setID(UmlViewsRepository.PackageMerge.Properties.receivingPackage);
		FormUtils.createHelpButton(widgetFactory, parent, propertiesEditionComponent.getHelpContent(UmlViewsRepository.PackageMerge.Properties.receivingPackage, UmlViewsRepository.FORM_KIND), null); //$NON-NLS-1$
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
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#getMergedPackage()
	 * 
	 */
	public EObject getMergedPackage() {
		if (mergedPackage.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) mergedPackage.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#initMergedPackage(EObjectFlatComboSettings)
	 */
	public void initMergedPackage(EObjectFlatComboSettings settings) {
		mergedPackage.setInput(settings);
		if (current != null) {
			mergedPackage.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#setMergedPackage(EObject newValue)
	 * 
	 */
	public void setMergedPackage(EObject newValue) {
		if (newValue != null) {
			mergedPackage.setSelection(new StructuredSelection(newValue));
		} else {
			mergedPackage.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#setMergedPackageButtonMode(ButtonsModeEnum newValue)
	 */
	public void setMergedPackageButtonMode(ButtonsModeEnum newValue) {
		mergedPackage.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#addFilterMergedPackage(ViewerFilter filter)
	 * 
	 */
	public void addFilterToMergedPackage(ViewerFilter filter) {
		mergedPackage.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#addBusinessFilterMergedPackage(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToMergedPackage(ViewerFilter filter) {
		mergedPackage.addBusinessRuleFilter(filter);
	}


	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#getReceivingPackage()
	 * 
	 */
	public EObject getReceivingPackage() {
		if (receivingPackage.getSelection() instanceof StructuredSelection) {
			Object firstElement = ((StructuredSelection) receivingPackage.getSelection()).getFirstElement();
			if (firstElement instanceof EObject)
				return (EObject) firstElement;
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#initReceivingPackage(EObjectFlatComboSettings)
	 */
	public void initReceivingPackage(EObjectFlatComboSettings settings) {
		receivingPackage.setInput(settings);
		if (current != null) {
			receivingPackage.setSelection(new StructuredSelection(settings.getValue()));
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#setReceivingPackage(EObject newValue)
	 * 
	 */
	public void setReceivingPackage(EObject newValue) {
		if (newValue != null) {
			receivingPackage.setSelection(new StructuredSelection(newValue));
		} else {
			receivingPackage.setSelection(new StructuredSelection()); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#setReceivingPackageButtonMode(ButtonsModeEnum newValue)
	 */
	public void setReceivingPackageButtonMode(ButtonsModeEnum newValue) {
		receivingPackage.setButtonMode(newValue);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#addFilterReceivingPackage(ViewerFilter filter)
	 * 
	 */
	public void addFilterToReceivingPackage(ViewerFilter filter) {
		receivingPackage.addFilter(filter);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.obeonetwork.dsl.uml2.properties.uml.parts.PackageMergePropertiesEditionPart#addBusinessFilterReceivingPackage(ViewerFilter filter)
	 * 
	 */
	public void addBusinessFilterToReceivingPackage(ViewerFilter filter) {
		receivingPackage.addBusinessRuleFilter(filter);
	}




	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.emf.eef.runtime.api.parts.IPropertiesEditionPart#getTitle()
	 * 
	 */
	public String getTitle() {
		return UmlMessages.PackageMerge_Part_Title;
	}

	// Start of user code additional methods
	
	// End of user code


}
