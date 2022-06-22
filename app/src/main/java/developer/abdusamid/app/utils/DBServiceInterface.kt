package developer.abdusamid.app.utils

import developer.abdusamid.app.models.Contact

interface DBServiceInterface {
    fun addContact(contact: Contact)
    fun deleteContact(contact: Contact)
    fun upDataContact(contact: Contact): Int
    fun getAllContact(): List<Contact>

}