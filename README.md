### Template Navigator
An Intellij plugin aims to help navigating between handlebar templates.

#### Features
- Show templates used by current template
- Show templates using current template
- Show locations(line number) where a template is used

![Tree-view](https://github.com/yutaodou/template-nav/blob/master/screenshots/template-reference-view.png)

#### Usage
- Right click in a template file, select context menu "Template Hierarchy" to open the template reference tree

![context-menu](https://github.com/yutaodou/template-nav/blob/master/screenshots/context-menu.png)

#### Notes
- The plugin analyses only templates that reside in a project's **src** directory. Add the directory containg your templates to the projects **src root** if not already included.
- To show icons for your templates in the tree view, add a file type definition for handlebar that matching your template's file extension.
- 

#### Package
[Template-Nav](https://github.com/yutaodou/template-nav/blob/master/TemplateNav-1.1.jar)
