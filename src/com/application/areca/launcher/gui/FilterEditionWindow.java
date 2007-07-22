package com.application.areca.launcher.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.application.areca.ArchiveFilter;
import com.application.areca.ResourceManager;
import com.application.areca.impl.FileSystemRecoveryTarget;
import com.application.areca.launcher.gui.common.AbstractWindow;
import com.application.areca.launcher.gui.common.SavePanel;
import com.application.areca.launcher.gui.filters.AbstractFilterComposite;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : -1700699344456460829
 */
 
 /*
 Copyright 2005-2007, Olivier PETRUCCI.
 
This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class FilterEditionWindow 
extends AbstractWindow {
    private static final ResourceManager RM = ResourceManager.instance();
    private static final String TITLE = RM.getLabel("filteredition.dialog.title");

    protected Combo cboFilterType;
    protected Button chkExclude;
    protected Button btnSave;
    protected AbstractFilterComposite pnlParams;
    protected Group pnlParamsContainer;
    
    protected ArchiveFilter currentFilter;  
    protected FileSystemRecoveryTarget currentTarget;
    
    public FilterEditionWindow(ArchiveFilter currentFilter, FileSystemRecoveryTarget currentTarget) {
        super();
        this.currentFilter = currentFilter;
        this.currentTarget = currentTarget;
    }

    protected Control createContents(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayout(new GridLayout(2, false));
        
        // TYPE
        Label lblFilterType = new Label(composite, SWT.NONE);
        lblFilterType.setText(RM.getLabel("filteredition.filtertypefield.label"));
        cboFilterType = new Combo(composite, SWT.READ_ONLY);
        cboFilterType.add(RM.getLabel("filteredition.fileextensionfilter.label"));
        cboFilterType.add(RM.getLabel("filteredition.regexfilter.label"));
        cboFilterType.add(RM.getLabel("filteredition.directoryfilter.label"));
        cboFilterType.add(RM.getLabel("filteredition.filesizefilter.label"));
        cboFilterType.add(RM.getLabel("filteredition.filedatefilter.label"));  
        cboFilterType.add(RM.getLabel("filteredition.linkfilter.label"));       
        cboFilterType.add(RM.getLabel("filteredition.lockedfilefilter.label"));   
        GridData dt = new GridData(SWT.FILL, SWT.CENTER, true, false);
        dt.widthHint = AbstractWindow.computeWidth(400);
        cboFilterType.setLayoutData(dt);
        
        cboFilterType.addSelectionListener(new SelectionListener() {
            public void widgetDefaultSelected(SelectionEvent e) {
            }
            public void widgetSelected(SelectionEvent e) {
                refreshParamPnl();
                registerUpdate();
            }
        });
        
        // EXCLUDE
        new Label(composite, SWT.NONE);
        chkExclude = new Button(composite, SWT.CHECK);
        chkExclude.setText(RM.getLabel("filteredition.exclusionfilterfield.label"));
        chkExclude.setToolTipText(RM.getLabel("filteredition.exclusionfilterfield.tooltip"));
        monitorControl(chkExclude);
        
        // CONTAINER
        pnlParamsContainer = new Group(composite, SWT.NONE);
        pnlParamsContainer.setText(RM.getLabel("filteredition.parametersfield.label"));
        GridLayout lt = new GridLayout();
        pnlParamsContainer.setLayout(lt);
        pnlParamsContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        
        // INIT
        if (this.currentFilter != null) {
            this.cboFilterType.setEnabled(false);
            chkExclude.setSelection(currentFilter.isExclude());
            cboFilterType.select(FilterRepository.getIndex(currentFilter));  
        } else {
            cboFilterType.select(0);
            chkExclude.setSelection(true);
        }
        
        buildParamPnl();
        
        // SAVE
        SavePanel sv = new SavePanel(this);
        Composite pnlSave = sv.buildComposite(composite);
        btnSave = sv.getBtnSave();
        pnlSave.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, false, 2, 1));
        
        composite.pack();
        return composite;
    }

    public String getTitle() {
        return TITLE;
    }
    
    public ArchiveFilter getCurrentFilter() {
        return currentFilter;
    }

    public FileSystemRecoveryTarget getCurrentTarget() {
        return currentTarget;
    }

    protected boolean checkBusinessRules() {
        if (pnlParams == null) {
            return true;
        } else {
            return pnlParams.validateParams();
        }
    }

    protected void saveChanges() {
        if (this.currentFilter == null) {
            this.currentFilter = FilterRepository.buildFilter(this.cboFilterType.getSelectionIndex());
        }
        this.currentFilter.setExclude(this.chkExclude.getSelection());
        if (pnlParams != null) {
            pnlParams.initFilter(currentFilter);
        }
        
        this.hasBeenUpdated = false;
        this.close();
    }

    protected void updateState(boolean rulesSatisfied) {
        btnSave.setEnabled(rulesSatisfied);
    }
    
    private void buildParamPnl() {
        this.pnlParams = FilterRepository.buildFilterComposite(
                this.cboFilterType.getSelectionIndex(), 
                this.pnlParamsContainer, 
                currentFilter, 
                this);
        
        GridData dt = new GridData(SWT.FILL, SWT.FILL, true, true);
        if (this.pnlParams != null) {
            this.pnlParams.setLayoutData(dt);
            this.pnlParamsContainer.setVisible(true);
        } else {
            this.pnlParamsContainer.setVisible(false);
        }
    }
    
    private void refreshParamPnl() {      
        if (pnlParams != null) {
            this.pnlParams.dispose();
            this.pnlParams = null;
            this.getShell().pack(true);
        }

        buildParamPnl();
        this.getShell().pack(true);
    }
}
