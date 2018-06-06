import { stringify } from 'qs'
import request from '../utils/request'

export function query (specification) {
  return request(`/apis/reap-facility/routes?${stringify(specification)}`, { method: 'GET' })
}

export function update (route) {
  return request('/apis/reap-facility/route', { method: 'PUT', body: route })
}

export function remove (id) {
  return request(`/apis/reap-facility/route/${id}`, { method: 'DELETE' })
}

export function create (route) {
  return request('/apis/reap-facility/route', { method: 'POST', body: route })
}

