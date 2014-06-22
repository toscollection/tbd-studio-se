package org.talend.oozie.scheduler.ui;

import org.eclipse.swt.widgets.Shell;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.ui.dialog.RepositoryReviewDialog;

public class SelectRepositoryDialog extends RepositoryReviewDialog {

    public SelectRepositoryDialog(Shell parent) {
        super(parent);

    }

    public SelectRepositoryDialog(Shell parentShell, ERepositoryObjectType type, String repositoryType) {
        super(parentShell, type, repositoryType);
    }

}
