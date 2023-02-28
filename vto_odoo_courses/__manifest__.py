{
    "name": "Odoo Course Management",
    "version": "14.0.2.0.1",
    "category": "Sales",
    "summary": "New group for seeing only sales channel's documents",
    "description": """This module aim to manage odoo courses (technical and functional).""",
    "depends": ["sale", "account"],
    # data harus urut
    "data": [
        "security/ir.model.access.csv",
        "views/menu_views.xml",
        "views/partner_views.xml",
        "views/course_views.xml",
    ],
    "demo": [],
    "installable": True,
    "auto_install": False,
    "assets": {},
    "license": "LGPL-3",
}
