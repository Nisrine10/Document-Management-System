import React, { useState, useEffect } from 'react';
import { Plus } from 'lucide-react';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import api from '../lib/api.ts';
import Navbar from '../components/Navbar.tsx';
import DocumentList from '../components/DocumentList.tsx';
import { useAuth } from '../contexts/AuthContext.tsx';

export default function Dashboard() {
  const [documents, setDocuments] = useState([]);
  const [mydocuments, setMyDocuments] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const { isAuthenticated, user } = useAuth();
  
  const [isModalOpen, setIsModalOpen] = useState(false); // For modal visibility
const [selectedDocumentId, setSelectedDocumentId] = useState(null); // The document being shared
const [users, setUsers] = useState([]); // List of users fetched from the API
const [selectedUserId, setSelectedUserId] = useState(null);

  const fetchUsers = async () => {
    try {
      const response = await api.get('users/all');
      return response.data;
    } catch (error) {
      toast.error('Failed to fetch users');
      return [];
    }
  };

  const handleShare = async (id: string) => {
    setSelectedDocumentId(id as any); // No more error here
    setIsModalOpen(true);
    const usersData = await fetchUsers();
    setUsers(usersData);
  };

  const shareDocumentWithUser = async () => {
    if (!selectedUserId) {
      toast.error('Please select a user to share the document with.');
      return;
    }

    try {
      await api.post('/partages/add', { documentId: selectedDocumentId, utilisateurId: selectedUserId });
      toast.success('Document shared successfully');
      setIsModalOpen(false);
    } catch (error) {
      toast.error('Failed to share document');
    }
  };
  
  
  


  if (!isAuthenticated) {
    return <p>You must be logged in to view this page.</p>;
  }

  useEffect(() => {
    fetchDocuments();
    fetchMyDocuments();
    fetchUsers();
    console.log(user)
  }, []);

  const fetchDocuments = async () => {
    try {
      const response = await api.get(`partages/documents/user/${user.id}`);
      setDocuments(response.data);
      console.log(response.data);
    } catch (error) {
      toast.error('Failed to fetch documents');
    } finally {
      setLoading(false);
    }
  };

  const fetchMyDocuments = async () => {
    try {
      const response = await api.get(`documents/proprietaire/${user.id}`);
      setMyDocuments(response.data);
      console.log(response.data);
    } catch (error) {
      toast.error('Failed to fetch documents');
    } finally {
      setLoading(false);
    }
  };

  const handleCreateDocument = async () => {
    try {
      const response = await api.post('/documents/add', {
        titre: 'Untitled Document',
        contenu: '',
        proprietaireId:user.id
      });
      toast.success('Document created');
      navigate(`/documents/${response.data.id}`);
    } catch (error) {
      toast.error('Failed to create document');
    }
  };

  const handleDelete = async (id: string) => {
    if (!window.confirm('Are you sure you want to delete this document?')) return;
    
    try {
      await api.delete(`/documents/delete/${id}`);
      toast.success('Document deleted');
      fetchDocuments();
      fetchMyDocuments();
    } catch (error) {
      toast.error('Failed to delete document');
    }
  };

 

  return (
    <div className="min-h-screen bg-gray-50">
      <Navbar />
      {isModalOpen && (
  <div className="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center z-50">
    <div className="bg-white p-6 rounded-lg shadow-md w-96">
      <h2 className="text-xl font-bold mb-4">Share Document</h2>
      <select
        id="user"
        className="w-full border-gray-300 rounded-md shadow-sm focus:border-indigo-500 focus:ring-indigo-500"
        value={selectedUserId || ''}
        onChange={async (e) => {
          const userId = e.target.value;
          setSelectedUserId(userId as any);
          
          // Trigger share logic immediately
          if (userId) {
            try {
              await api.post('/partages/add', { documentId: selectedDocumentId, utilisateurId: userId });
              toast.success('Document shared successfully');
              setIsModalOpen(false); // Close the modal after sharing
            } catch (error) {
              toast.error('Failed to share document');
            }
          }
        }}
      >
        <option value="" disabled>
          -- Select a User --
        </option>
        {users.map((user: any) => (
          <option key={user.id} value={user.id}>
            {user.name} ({user.email})
          </option>
        ))}
      </select>
      <button
        onClick={() => setIsModalOpen(false)}
        className="mt-4 px-4 py-2 bg-gray-500 text-white rounded hover:bg-gray-600"
      >
        Close
      </button>
    </div>
  </div>
)}


      <main className="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
      <div className="px-4 sm:px-0">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-2xl font-bold text-gray-900">Mes Documents{}</h1>
            <button 
              onClick={handleCreateDocument}
              className="inline-flex items-center gap-2 px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-indigo-600 hover:bg-indigo-700"
            >
              <Plus className="h-4 w-4" />
              New Document
            </button>
          </div>
          
          {loading ? (
            <div className="text-center py-12">
              <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600 mx-auto"></div>
            </div>
          ) : mydocuments.length === 0 ? (
            <div className="text-center py-12">
              <p className="text-gray-500">No documents yet. Create your first one!</p>
            </div>
          ) : (
            <DocumentList
              documents={mydocuments}
              onDelete={handleDelete}
              onShare={handleShare}
            />
          )}
        </div>
        <div className="px-10 mt-10 sm:px-0">
          <div className="flex justify-between items-center mb-6">
            <h1 className="text-2xl font-bold text-gray-900">Documents Partages</h1>
            
          </div>
          
          {loading ? (
            <div className="text-center py-12">
              <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-indigo-600 mx-auto"></div>
            </div>
          ) : documents.length === 0 ? (
            <div className="text-center py-12">
              <p className="text-gray-500">No documents yet. Create your first one!</p>
            </div>
          ) : (
            <DocumentList
              documents={documents}
              onDelete={handleDelete}
              onShare={handleShare}
            />
          )}
        </div>
       
      </main>
    </div>
  );
}