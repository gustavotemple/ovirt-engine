package org.ovirt.engine.ui.common.view.popup;

import java.util.ArrayList;

import org.ovirt.engine.ui.common.editor.UiCommonEditorDriver;
import org.ovirt.engine.ui.common.idhandler.ElementIdHandler;
import org.ovirt.engine.ui.common.presenter.popup.DefaultConfirmationPopupPresenterWidget;
import org.ovirt.engine.ui.common.widget.dialog.SimpleDialogPanel;
import org.ovirt.engine.ui.uicommonweb.models.ConfirmationModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;

public class DefaultConfirmationPopupView extends AbstractConfirmationPopupView implements DefaultConfirmationPopupPresenterWidget.ViewDef {

    interface Driver extends UiCommonEditorDriver<ConfirmationModel, DefaultConfirmationPopupView> {
    }

    interface ViewUiBinder extends UiBinder<SimpleDialogPanel, DefaultConfirmationPopupView> {
        ViewUiBinder uiBinder = GWT.create(ViewUiBinder.class);
    }

    interface ViewIdHandler extends ElementIdHandler<DefaultConfirmationPopupView> {
        ViewIdHandler idHandler = GWT.create(ViewIdHandler.class);
    }

    @UiField
    FlowPanel descriptionPanel;

    private final Driver driver = GWT.create(Driver.class);

    @Inject
    public DefaultConfirmationPopupView(EventBus eventBus) {
        super(eventBus);
        initWidget(ViewUiBinder.uiBinder.createAndBindUi(this));
        ViewIdHandler.idHandler.generateAndSetIds(this);
        driver.initialize(this);
    }

    @Override
    public void edit(final ConfirmationModel object) {
        driver.edit(object);

        object.getItemsChangedEvent().addListener((ev, sender, args) -> {
            ArrayList<String> items = (ArrayList<String>) object.getItems();

            for (String item : items) {
                descriptionPanel.add(new Label(getItemTextFormatted(item)));
            }
        });
    }

    private String getItemTextFormatted(String itemText) {
        return "- " + itemText; //$NON-NLS-1$ //$NON-NLS-2$
    }

    @Override
    public ConfirmationModel flush() {
        return driver.flush();
    }

    @Override
    public void cleanup() {
        driver.cleanup();
    }
}
