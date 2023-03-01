from odoo import models, fields, api, _

class OdooPartnerAttendees(models.Model):
    # _name = "odoo.partner.attendees"
    _inherit = "res.partner"
    # _description = "Odoo Partner Attendees"

    attendees_number = fields.Char(string="Attendees Number",required=True, help="e.g 9523416")

    @api.onchange("attendees_number")
    def _onchange_name(self):
        for rec in self:
            if rec.attendees_number:
                # if len(rec.attendees_number) < 6 and str(rec.attendees_number[0]) != "9" :
                if len(rec.attendees_number) < 6 or rec.attendees_number.startswith('9')==False or rec.attendees_number.isdigit()==False:
                    return {
                        "warning": {
                            "title": _("Oops"),
                            "message": _("Min 6 Characters, All characters must be a number, Characters must start with 9"),
                        }
                    }

class OdooPartnerInstructor(models.Model):
    # _name = "odoo.partner.instructors"
    _inherit = "res.partner"
    # _description = "Odoo Partner Instructor"

    instructors_number = fields.Char(string="Instructors Number",required=True, help="e.g 9523416")

    @api.onchange("instructors_number")
    def _onchange_name(self):
        for rec in self:
            if rec.instructors_number:
                # if len(rec.instructors_number) < 6 and str(rec.instructors_number[0]) != "9" :
                if len(rec.instructors_number) < 6 or rec.instructors_number.startswith('9')==False or rec.instructors_number.isdigit()==False:
                    return {
                        "warning": {
                            "title": _("Oops"),
                            "message": _("Min 6 Characters, All characters must be a number, Characters must start with 9"),
                        }
                    }