 
{
    "name": "Odoo Course Management",
    "version": "14.0.2.0.1",
    "category": "Sales",
    "summary": "New group for seeing only sales channel's documents",
    "description": """
    This module aim to manage odoo courses (technical and functional)
    """
    "depends": ["sale","account"],
    "data": ["security/sales_team_security.xml", "views/res_partner_view.xml"],
    "website": "https://github.com/OCA/sale-workflow",
    "author": "Tecnativa, Odoo Community Association (OCA)",
    "license": "AGPL-3",
    "installable": True,
    "development_status": "Production/Stable",
    "maintainers": ["pedrobaeza", "ivantodorovich"],
    "post_init_hook": "post_init_hook",
    "uninstall_hook": "uninstall_hook",
}
