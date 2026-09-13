const API_BASE = '/api'

async function request(path, options) {
  const res = await fetch(API_BASE + path, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  })

  if (res.status === 204) {
    return null
  }

  const body = await res.json().catch(() => null)

  if (!res.ok) {
    const message = body?.message || `Request failed (${res.status})`
    throw new Error(message)
  }

  return body
}

export const api = {
  getCurrentSession: () => request('/sessions/current'),
  getGarden: () => request('/garden'),
  startSession: (minutes) =>
    request('/sessions', { method: 'POST', body: JSON.stringify({ minutes }) }),
  completeSession: (id) => request(`/sessions/${id}/complete`, { method: 'POST' }),
  abandonSession: (id) => request(`/sessions/${id}/abandon`, { method: 'POST' }),
}
