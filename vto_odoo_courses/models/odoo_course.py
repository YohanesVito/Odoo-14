from odoo import models, fields, api, _


class OdooCourse(models.Model):
    _name = "odoo.course"
    _description = "Odoo Course"

    name = fields.Char(string="Name", required=True, help="Name of the course")
    instructor_id = fields.Many2one(
        "res.partner",
        string="Instructor",
        required=True,
        domain=[("is_company", "=", False)],
        help="The instructor who will teach the course",
    )
    notes = fields.Text(
        string="Description",
        help="Short description",
    )

    @api.onchange("name")
    def _onchange_name(self):
        for rec in self:
            if rec.name:
                if len(rec.name) < 10:
                    return {
                        "warning": {
                            "title": _("Oops"),
                            "message": _("Min 10 Characters"),
                        }
                    }
