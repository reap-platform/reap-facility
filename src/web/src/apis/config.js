import { stringify } from 'qs'
import request from '../utils/request'

export function query (specification) {
  return request(`/apis/reap-facility/configs?${stringify(specification)}`, { method: 'GET' })
}

export function update (config) {
  return request('/apis/reap-facility/config', { method: 'PUT', body: config })
}

export function remove (id) {
  return request(`/apis/reap-facility/config/${id}`, { method: 'DELETE' })
}

export function create (config) {
  return request('/apis/reap-facility/config', { method: 'POST', body: config })
}

