export interface Usuario {
id?: number;
nombre: string;
apellido: string;
correo: string;
telefono?: string;
contrasena: string;
fechaRegistro?: Date; // o String
rol?: String;
}
