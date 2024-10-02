#language: es
#Este es un ejemplo de automatización
#INICIALIZADOR DE CASOS DE VALIDACION POR CRM ONE Y SERVICIOS SOAP
#Fecha de modificación 09-11-2022
#Autor Jesús Peñaloza
#Actualización: Se Mejora Flujo de Agregar Servicios (LDI y Roaming).
#Fecha de modificación 30-11-2022
#Autor Jesús Peñaloza
#Actualización: Se agrega consulta por Rut, para obtener ContactId para Persona.
#Actualización: Se agrega Venta sobre un Rut Existente, para Flujo Venta por Servicio
#Actualización: Se mejora el uso del servicio ProvideWirelessIVR, dejando todo en una función.
Característica: Login
  Validar que las acciones de Login

Esquema del escenario: Abrir Pagina Inicial de Claro
   Dado Se da inicio Amdocs
Ejemplos:
||
||
