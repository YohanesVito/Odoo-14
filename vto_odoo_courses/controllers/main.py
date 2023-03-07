import binascii

from odoo import fields, http, SUPERUSER_ID, _
from odoo.exceptions import AccessError, MissingError
from odoo.http import request
from odoo.addons.payment.controllers.portal import PaymentProcessing
from odoo.addons.portal.controllers.mail import _message_post_helper
from odoo.addons.portal.controllers.portal import CustomerPortal, pager as portal_pager, get_records_pager
from odoo.osv import expression

class CustomerPortal(CustomerPortal):

    def _prepare_home_portal_values(self, counters):
        values = super()._prepare_home_portal_values(counters)

        if 'courses_count' in counters:
            values['courses_count'] = 9999

        print("-----counters-----",values)

        return values
    
    @http.route(['/my/courses','/my/courses/page/<int:page>'], type='http', auth='public', website=True)
    def portal_my_courses(self, page=1, date_begin=None, date_end=None, sortby=None, **kw):
        
        courses = request.env['odoo.course'].sudo().search([])
        course = courses and courses[0] or request.env['odoo.course']

        print('==courses==',courses, '>>>>',course)
        values = {
            'page_name': 'courses',
            'courses': courses,
            'course':course,
            'courses_count': len(courses),
        }
        return request.render["vto_odoo_courses.portal_my_courses",values]