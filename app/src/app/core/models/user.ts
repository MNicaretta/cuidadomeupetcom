export interface User {
  id: number | undefined,
  revision: number | undefined,
  name: string,
  email: string,
  password: string,
  identity?: string,
  phone?: string,
  description?: string,
  createdDate: Date
}
