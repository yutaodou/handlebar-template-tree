### Template Navigator
An Intellij plugin aims to help navigating between handlebar templates.

#### Features
- Show templates used by current template
- Show templates using current template
- Show locations(line number) where a template is used



#### Usage
- Right click in a template file, select context menu "Template Hierarchy" to open the template reference tree

#### Notes
- The plugin analyses only templates that reside in a project's **src** directory. Add the directory containg your templates to the projects **src root** if not already included.
- To show icons for your templates in the tree view, add a file type definition for handlebar that matching your template's file extension.