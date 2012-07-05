package de.berlin.fu.shared.actions;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.util.SC;

import de.berlin.fu.data.dto.Event;
import de.berlin.fu.data.dto.EventType;

public class DialogBoxAction implements IAction {

	public static DialogBox alertWidget(final String header,
			final String content) {
		final DialogBox box = new DialogBox();
		final VerticalPanel panel = new VerticalPanel();
		box.setText(header);
		panel.add(new Label(content));
		final Button buttonClose = new Button("Close", new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				box.hide();
			}
		});
		// few empty labels to make widget larger
		final Label emptyLabel = new Label("");
		emptyLabel.setSize("auto", "25px");
		panel.add(emptyLabel);
		panel.add(emptyLabel);
		buttonClose.setWidth("90px");
		panel.add(buttonClose);
		panel.setCellHorizontalAlignment(buttonClose, HasAlignment.ALIGN_RIGHT);
		box.add(panel);
		return box;
	}

	@Override
	public void execute(Event e, EventType et) {
		SC.say("Alert: " + et.getName(),
				"Event description: " + et.getDescription() + "<br>" + "Time: "
						+ e.getTimestamp());

		// alertWidget(
		// "Alert: " + et.getName(),
		// "Event description: " + et.getDescription() + "<br>" + "Time: "
		// + e.getTimestamp()).show();

	}

	@Override
	public boolean isClientSide() {
		return true;
	}

	@Override
	public boolean isServerSide() {
		return false;
	}

}
