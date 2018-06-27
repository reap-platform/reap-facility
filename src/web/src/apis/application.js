import { stringify } from 'qs'
import request from '../utils/request'

export function query (specification) {
  return request(`/apis/reap-facility/applications?${stringify(specification)}`, { method: 'GET' })
}

export function queryAll () {
  return request('/apis/reap-facility/applications/all', { method: 'GET' })
}

export function update (data) {
  return request('/apis/reap-facility/application', { method: 'PUT', body: data })
}

export function remove (id) {
  return request(`/apis/reap-facility/application/${id}`, { method: 'DELETE' })
}

export function create (data) {
  return request('/apis/reap-facility/application', { method: 'POST', body: data })
}

